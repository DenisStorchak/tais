package ua.org.tees.yarosh.tais.homework;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
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

import static ua.org.tees.yarosh.tais.core.common.CacheNames.MANUAL_TASK;
import static ua.org.tees.yarosh.tais.core.common.CacheNames.QUESTIONS_SUITE;
import static ua.org.tees.yarosh.tais.homework.util.TaskUtils.*;

@Service
public class DefaultHomeworkManager implements HomeworkManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultHomeworkManager.class);
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
    private ManualTaskResultRepository manualTaskResultRepository;
    @Autowired
    private AchievementDiaryRepository diaryRepository;

    @Override
    @CacheEvict(MANUAL_TASK)
    public long createManualTask(ManualTask task) {
        LOGGER.info("Try to create manual task [{}]", task.getDescription());
        switchManualTaskState(task, true);
        return task.getId();
    }

    @Override
    @CacheEvict(QUESTIONS_SUITE)
    public long createQuestionsSuite(QuestionsSuite questionsSuite) {
        LOGGER.info("Try to create questions suite [{}]", questionsSuite.getTheme());
        switchQuestionsSuiteState(questionsSuite, true);
        return questionsSuite.getId();
    }

    @Override
    @CacheEvict(QUESTIONS_SUITE)
    public void enableQuestionsSuite(long id) {
        QuestionsSuite questionsSuite = questionsSuiteRepository.findOne(id);
        if (!questionsSuite.getEnabled()) {
            switchQuestionsSuiteState(questionsSuite, true);
        }
    }

    @Override
    @CacheEvict(QUESTIONS_SUITE)
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
        for (Registrant student : studentGroup.getStudents()) {
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
    @Cacheable(MANUAL_TASK)
    public List<ManualTask> findManualTasks(StudentGroup studentGroup) {
        return manualTaskRepository.findByStudentGroup(studentGroup);
    }

    @Override
    @Cacheable(QUESTIONS_SUITE)
    public List<QuestionsSuite> findQuestionsSuites(StudentGroup studentGroup) {
        return questionsSuiteRepository.findOne(studentGroup);
    }

    @Override
    @Cacheable(MANUAL_TASK)
    public ManualTaskReport getManualTaskResult(Registrant registrant, ManualTask manualTask) {
        return manualTaskResultRepository.findOne(manualTask, registrant);
    }

    @Override
    @CacheEvict(MANUAL_TASK)
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
    @Cacheable(MANUAL_TASK)
    public List<ManualTask> findUnresolvedManualTasksBeforeDeadline(Registrant registrant, int daysBefore) {
        return personalTaskHolderRepository.findOne(registrant).getManualTaskList().stream()
                .filter(t -> isDeadlineAfter(t, daysBefore))
                .filter(t -> !isTaskOverdue(t))
                .filter(t -> manualTaskResultRepository.findOne(t, registrant) == null)
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(MANUAL_TASK)
    public List<ManualTask> findUnresolvedActualManualTasks(Registrant registrant) {
        return personalTaskHolderRepository.findOne(registrant).getManualTaskList().stream()
                .filter(t -> !isTaskOverdue(t))
                .filter(t -> manualTaskResultRepository.findOne(t, registrant) == null)
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(MANUAL_TASK)
    public List<ManualTaskReport> findUnratedManualTaskResults(Discipline discipline) {
        return manualTaskResultRepository.findByDiscipline(discipline).stream()
                .filter(r -> !isRated(r.getTask(), diaryRepository.findOne(r.getOwner()).getManualAchievements()))
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(QUESTIONS_SUITE)
    public List<QuestionsSuite> findUnresolvedQuestionsSuiteBeforeDeadline(Registrant registrant, int daysBeforeDeadline) {
        return personalTaskHolderRepository.findOne(registrant).getQuestionsSuiteList().stream()
                .filter(q -> isDeadlineAfter(q, daysBeforeDeadline))
                .filter(q -> !isTaskOverdue(q))
                .filter(q -> !isRated(q, diaryRepository.findOne(registrant).getAutoAchievements()))
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(QUESTIONS_SUITE)
    public List<QuestionsSuite> findUnresolvedActualQuestionsSuite(Registrant registrant) {
        return personalTaskHolderRepository.findOne(registrant).getQuestionsSuiteList().stream()
                .filter(q -> !isTaskOverdue(q))
                .filter(q -> !isRated(q, diaryRepository.findOne(registrant).getAutoAchievements()))
                .collect(Collectors.toList());
    }

    @Override
    @CacheEvict(MANUAL_TASK)
    public void enableManualTask(long id) {
        ManualTask manualTask = manualTaskRepository.findOne(id);
        if (manualTask.isEnabled()) {
            switchManualTaskState(manualTask, true);
        }
    }

    @Override
    @CacheEvict(MANUAL_TASK)
    public void disableManualTask(long id) {
        ManualTask manualTask = manualTaskRepository.findOne(id);
        if (!manualTask.isEnabled()) {
            switchManualTaskState(manualTask, false);
        }
    }

    private void switchManualTaskState(ManualTask manualTask, boolean enable) {
        manualTask.setEnabled(enable);
        manualTaskRepository.saveAndFlush(manualTask);
        StudentGroup studentGroup = studentGroupRepository.findOne(manualTask.getStudentGroup().getId());
        for (Registrant student : studentGroup.getStudents()) {
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
