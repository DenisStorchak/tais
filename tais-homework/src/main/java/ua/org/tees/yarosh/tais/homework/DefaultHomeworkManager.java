package ua.org.tees.yarosh.tais.homework;

import com.google.common.eventbus.AsyncEventBus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ua.org.tees.yarosh.tais.core.common.models.Discipline;
import ua.org.tees.yarosh.tais.core.common.models.Registrant;
import ua.org.tees.yarosh.tais.core.common.models.StudentGroup;
import ua.org.tees.yarosh.tais.core.user.mgmt.api.persistence.StudentGroupRepository;
import ua.org.tees.yarosh.tais.homework.api.HomeworkManager;
import ua.org.tees.yarosh.tais.homework.api.persistence.*;
import ua.org.tees.yarosh.tais.homework.events.*;
import ua.org.tees.yarosh.tais.homework.models.*;

import java.util.List;
import java.util.stream.Collectors;

import static ua.org.tees.yarosh.tais.core.common.CacheNames.*;
import static ua.org.tees.yarosh.tais.homework.util.TaskUtils.*;

@Service
public class DefaultHomeworkManager implements HomeworkManager {

    private static final Logger log = LoggerFactory.getLogger(DefaultHomeworkManager.class);
    private ManualTaskRepository manualTaskRepository;
    private StudentGroupRepository studentGroupRepository;
    private PersonalTaskHolderRepository personalTaskHolderRepository;
    private QuestionsSuiteRepository questionsSuiteRepository;
    private ManualTaskReportRepository manualTaskReportRepository;
    private AchievementDiaryRepository diaryRepository;
    private AsyncEventBus eventbus;

    @Autowired
    public void setManualTaskRepository(ManualTaskRepository manualTaskRepository) {
        this.manualTaskRepository = manualTaskRepository;
    }

    @Autowired
    public void setStudentGroupRepository(StudentGroupRepository studentGroupRepository) {
        this.studentGroupRepository = studentGroupRepository;
    }

    @Autowired
    public void setPersonalTaskHolderRepository(PersonalTaskHolderRepository personalTaskHolderRepository) {
        this.personalTaskHolderRepository = personalTaskHolderRepository;
    }

    @Autowired
    public void setQuestionsSuiteRepository(QuestionsSuiteRepository questionsSuiteRepository) {
        this.questionsSuiteRepository = questionsSuiteRepository;
    }

    @Autowired
    public void setManualTaskReportRepository(ManualTaskReportRepository manualTaskReportRepository) {
        this.manualTaskReportRepository = manualTaskReportRepository;
    }

    @Autowired
    public void setDiaryRepository(AchievementDiaryRepository diaryRepository) {
        this.diaryRepository = diaryRepository;
    }

    @Autowired
    public void setEventbus(AsyncEventBus eventbus) {
        this.eventbus = eventbus;
    }

    @Override
    @CacheEvict(MANUAL_TASKS)
    public long createManualTask(ManualTask task) {
        log.info("Try to create manual task [{}]", task.getDescription());
        switchManualTaskState(task, true);
        return task.getId();
    }

    @Override
    @CacheEvict(QUESTION_SUITES)
    public long createQuestionsSuite(QuestionsSuite questionsSuite) {
        log.info("Try to create questions suite [{}]", questionsSuite.getTheme());
        switchQuestionsSuiteState(questionsSuite, true);
        return questionsSuite.getId();
    }

    @Override
    public QuestionsSuite findQuestionsSuite(long id) {
        return questionsSuiteRepository.findOne(id);
    }

    @Override
    @CachePut(QUESTION_SUITES)
    public void enableQuestionsSuite(long id) {
        QuestionsSuite questionsSuite = questionsSuiteRepository.findOne(id);
        if (!questionsSuite.getEnabled()) {
            switchQuestionsSuiteState(questionsSuite, true);
        }
    }

    @Override
    public void addQuestionsSuiteEnabledListener(QuestionsSuiteEnabledListener listener) {
        eventbus.register(listener);
    }

    @Override
    @CachePut(QUESTION_SUITES)
    public void disableQuestionsSuite(long id) {
        QuestionsSuite questionsSuite = questionsSuiteRepository.findOne(id);
        if (questionsSuite.getEnabled()) {
            switchQuestionsSuiteState(questionsSuite, false);
        }
    }

    @Override
    public void addQuestionsSuiteDisabledListener(QuestionsSuiteDisabledListener listener) {
        eventbus.register(listener);
    }

    private void switchQuestionsSuiteState(QuestionsSuite questionsSuite, boolean enable) {
        questionsSuite.setEnabled(enable);
        questionsSuiteRepository.saveAndFlush(questionsSuite);
        StudentGroup studentGroup = studentGroupRepository.findOne(questionsSuite.getStudentGroup().getId());
        for (Registrant student : studentGroupRepository.findRegistrantsByStudentGroup(studentGroup.getId())) {
            PersonalTaskHolder taskHolder = personalTaskHolderRepository.findOne(student);
            if (enable) {
                taskHolder.getQuestionsSuiteList().add(questionsSuite);
            } else {
                taskHolder.getQuestionsSuiteList().remove(questionsSuite);
            }
            personalTaskHolderRepository.saveAndFlush(taskHolder);
        }
        if (enable) eventbus.post(new QuestionsSuiteEnabledEvent(questionsSuite));
        else eventbus.post(new QuestionsSuiteDisabledEvent(questionsSuite));
    }

    @Override
    @Cacheable(MANUAL_TASKS)
    public List<ManualTask> findManualTasks(StudentGroup studentGroup) {
        return manualTaskRepository.findByStudentGroup(studentGroup);
    }

    @Override
    @Cacheable(QUESTION_SUITES)
    public List<QuestionsSuite> findQuestionsSuites(StudentGroup studentGroup) {
        List<QuestionsSuite> suites = questionsSuiteRepository.findAllByStudentGroup(studentGroup);
        log.info("[{}] questions suites found", suites.size());
        return suites;
    }

    @Override
    @Cacheable(MANUAL_TASK_RESULTS)
    public ManualTaskReport getManualTaskReport(Registrant registrant, ManualTask manualTask) {
        return manualTaskReportRepository.findOne(manualTask, registrant);
    }

    @Override // todo evict diary cache
    public void rate(ManualTaskReport manualTaskReport, Registrant examiner, int grade) {
        AchievementDiary diary = diaryRepository.findOne(manualTaskReport.getOwner());
        ManualAchievement manualAchievement = new ManualAchievement();
        manualAchievement.setExaminer(examiner);
        manualAchievement.setGrade(grade);
        manualAchievement.setManualTask(manualTaskReport.getTask());
        diary.getManualAchievements().add(manualAchievement);
        diaryRepository.saveAndFlush(diary);
        eventbus.post(new ReportRatedEvent(manualTaskReport, examiner, grade));
    }

    @Override
    public void addManualTaskRatedListener(ManualTaskRatedListener listener) {
        eventbus.register(listener);
    }

    @Override
    @Cacheable(MANUAL_TASKS)
    public List<ManualTask> findUnresolvedManualTasksBeforeDeadline(Registrant registrant, int daysBefore) {
        return personalTaskHolderRepository.findOne(registrant).getManualTaskList().stream()
                .filter(t -> isDeadlineAfter(t, daysBefore))
                .filter(t -> !isTaskOverdue(t))
                .filter(t -> manualTaskReportRepository.findOne(t, registrant) == null)
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(MANUAL_TASKS)
    public List<ManualTask> findUnresolvedActualManualTasks(Registrant registrant) {
        return personalTaskHolderRepository.findOne(registrant).getManualTaskList().stream()
                .filter(t -> !isTaskOverdue(t))
                .filter(t -> manualTaskReportRepository.findOne(t, registrant) == null)
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(MANUAL_TASK_RESULTS)
    public List<ManualTaskReport> findUnratedManualTaskReports(Discipline discipline) {
        return manualTaskReportRepository.findByDiscipline(discipline).stream()
                .filter(r -> !isRated(r.getTask(), diaryRepository.findOne(r.getOwner()).getManualAchievements()))
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(QUESTION_SUITES)
    public List<QuestionsSuite> findUnresolvedQuestionsSuiteBeforeDeadline(Registrant registrant, int daysBeforeDeadline) {
        return personalTaskHolderRepository.findOne(registrant).getQuestionsSuiteList().stream()
                .filter(q -> isDeadlineAfter(q, daysBeforeDeadline))
                .filter(q -> !isTaskOverdue(q))
                .filter(q -> !isRated(q, diaryRepository.findOne(registrant).getAutoAchievements()))
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(QUESTION_SUITES)
    public List<QuestionsSuite> findUnresolvedActualQuestionsSuite(Registrant registrant) {
        return personalTaskHolderRepository.findOne(registrant).getQuestionsSuiteList().stream()
                .filter(q -> !isTaskOverdue(q))
                .filter(q -> !isRated(q, diaryRepository.findOne(registrant).getAutoAchievements()))
                .collect(Collectors.toList());
    }

    @Override
    @CacheEvict(value = MANUAL_TASKS, allEntries = true)
    public void enableManualTask(long id) {
        ManualTask manualTask = manualTaskRepository.findOne(id);
        if (manualTask.isEnabled()) {
            switchManualTaskState(manualTask, true);
        }
    }

    @Override
    public void addManualTaskEnabledListener(ManualTaskEnabledListener listener) {
        eventbus.register(listener);
    }

    @Override
    @CacheEvict(value = MANUAL_TASKS, allEntries = true)
    public void disableManualTask(long id) {
        ManualTask manualTask = manualTaskRepository.findOne(id);
        if (!manualTask.isEnabled()) {
            switchManualTaskState(manualTask, false);
        }
    }

    @Override
    public void addManualTaskDisabledListener(ManualTaskDisabledListener listener) {
        eventbus.register(listener);
    }

    private void switchManualTaskState(ManualTask manualTask, boolean enable) {
        manualTask.setEnabled(enable);
        manualTaskRepository.saveAndFlush(manualTask);
        StudentGroup studentGroup = studentGroupRepository.findOne(manualTask.getStudentGroup().getId());
        for (Registrant student : studentGroupRepository.findRegistrantsByStudentGroup(studentGroup.getId())) {
            PersonalTaskHolder taskHolder = personalTaskHolderRepository.findOne(student);
            if (enable) taskHolder.getManualTaskList().add(manualTask);
            else taskHolder.getManualTaskList().remove(manualTask);
            personalTaskHolderRepository.saveAndFlush(taskHolder);
        }
        if (enable) eventbus.post(new ManualTaskEnabledEvent(manualTask));
        else eventbus.post(new ManualTaskDisabledEvent(manualTask));
    }
}
