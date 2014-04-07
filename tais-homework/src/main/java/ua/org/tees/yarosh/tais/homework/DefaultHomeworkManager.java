package ua.org.tees.yarosh.tais.homework;

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
import ua.org.tees.yarosh.tais.homework.models.*;

import java.util.List;
import java.util.stream.Collectors;

import static ua.org.tees.yarosh.tais.core.common.CacheNames.*;
import static ua.org.tees.yarosh.tais.homework.util.TaskUtils.*;

@Service
public class DefaultHomeworkManager implements HomeworkManager {

    private static final Logger log = LoggerFactory.getLogger(DefaultHomeworkManager.class);
    @Autowired
    private ManualTaskRepository manualTaskRepository;
    @Autowired
    private StudentGroupRepository studentGroupRepository;
    @Autowired
    private PersonalTaskHolderRepository personalTaskHolderRepository;
    @Autowired
    private TaskHolderRepository taskHolderRepository;
    @Autowired
    private QuestionsSuiteRepository questionsSuiteRepository;
    @Autowired
    private ManualTaskReportRepository manualTaskReportRepository;
    @Autowired
    private AchievementDiaryRepository diaryRepository;

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
    @CachePut(QUESTION_SUITES)
    public void enableQuestionsSuite(long id) {
        QuestionsSuite questionsSuite = questionsSuiteRepository.findOne(id);
        if (!questionsSuite.getEnabled()) {
            switchQuestionsSuiteState(questionsSuite, true);
        }
    }

    @Override
    @CachePut(QUESTION_SUITES)
    public void disableQuestionsSuite(long id) {
        QuestionsSuite questionsSuite = questionsSuiteRepository.findOne(id);
        if (!questionsSuite.getEnabled()) {
            switchQuestionsSuiteState(questionsSuite, false);
        }
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
    }

    @Override
    @Cacheable(MANUAL_TASKS)
    public List<ManualTask> findManualTasks(StudentGroup studentGroup) {
        return manualTaskRepository.findByStudentGroup(studentGroup);
    }

    @Override
    @Cacheable(QUESTION_SUITES)
    public List<QuestionsSuite> findQuestionsSuites(StudentGroup studentGroup) {
        return questionsSuiteRepository.findOne(studentGroup);
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
    @CacheEvict(value = MANUAL_TASKS, key = "#id")
    public void enableGroupTask(long id) {
        ManualTask manualTask = manualTaskRepository.findOne(id);
        if (manualTask.isEnabled()) {
            switchManualTaskState(manualTask, true);
        }
    }

    @Override
    @CacheEvict(value = MANUAL_TASKS, key = "#id")
    public void disableGroupTask(long id) {
        ManualTask manualTask = manualTaskRepository.findOne(id);
        if (!manualTask.isEnabled()) {
            switchManualTaskState(manualTask, false);
        }
    }

    private void switchManualTaskState(ManualTask manualTask, boolean enable) {
        manualTask.setEnabled(enable);
        manualTaskRepository.saveAndFlush(manualTask);
        StudentGroup studentGroup = studentGroupRepository.findOne(manualTask.getStudentGroup().getId());
        for (Registrant student : studentGroupRepository.findRegistrantsByStudentGroup(studentGroup.getId())) {
            PersonalTaskHolder taskHolder = personalTaskHolderRepository.findOne(student);
            if (enable) {
                taskHolder.getManualTaskList().add(manualTask);
            } else {
                taskHolder.getManualTaskList().remove(manualTask);
            }
            taskHolderRepository.saveAndFlush(taskHolder);
        }
    }
}
