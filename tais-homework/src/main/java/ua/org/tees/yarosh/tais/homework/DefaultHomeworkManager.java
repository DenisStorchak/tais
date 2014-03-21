package ua.org.tees.yarosh.tais.homework;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.org.tees.yarosh.tais.core.common.models.Discipline;
import ua.org.tees.yarosh.tais.core.common.models.Registrant;
import ua.org.tees.yarosh.tais.core.common.models.StudentGroup;
import ua.org.tees.yarosh.tais.core.user.mgmt.api.persistence.StudentGroupRepository;
import ua.org.tees.yarosh.tais.homework.api.HomeworkManager;
import ua.org.tees.yarosh.tais.homework.api.persistence.*;
import ua.org.tees.yarosh.tais.homework.models.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static ua.org.tees.yarosh.tais.homework.TimeUtils.minusDays;

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
    private AchievementDiaryRepository achievementDiaryRepository;

    @Override
    public long createGeneralTask(ManualTask task) {
        LOGGER.info("Try to create manual task [{}]", task.getDescription());
        switchManualTaskState(task, true);
        return task.getId();
    }

    @Override
    public long createQuestionsSuite(QuestionsSuite questionsSuite) {
        LOGGER.info("Try to create questions suite [{}]", questionsSuite.getTheme());
        switchQuestionsSuiteState(questionsSuite, true);
        return questionsSuite.getId();
    }

    @Override
    public void enableQuestionsSuite(long id) {
        QuestionsSuite questionsSuite = questionsSuiteRepository.findOne(id);
        if (!questionsSuite.getEnabled()) {
            switchQuestionsSuiteState(questionsSuite, true);
        }
    }

    @Override
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
    public List<ManualTask> findManualTasks(StudentGroup studentGroup) {
        return manualTaskRepository.findByStudentGroup(studentGroup);
    }

    @Override
    public List<QuestionsSuite> findQuestionsSuites(StudentGroup studentGroup) {
        return questionsSuiteRepository.findOne(studentGroup);
    }

    @Override
    public ManualTaskResult getManualTaskResult(Registrant registrant, ManualTask manualTask) {
        return manualTaskResultRepository.findOne(manualTask, registrant);
    }

    @Override
    public void rate(ManualTaskResult manualTaskResult, Registrant examiner, int grade) {
        AchievementDiary diary = achievementDiaryRepository.findOne(manualTaskResult.getOwner());
        ManualAchievement manualAchievement = new ManualAchievement();
        manualAchievement.setExaminer(examiner);
        manualAchievement.setGrade(grade);
        manualAchievement.setManualTask(manualTaskResult.getTask());
        diary.getManualAchievements().add(manualAchievement);
        achievementDiaryRepository.saveAndFlush(diary);
    }

    @Override
    public List<ManualTask> findUnresolvedManualTasksBeforeDeadline(Registrant registrant, int daysBefore) {
        PersonalTaskHolder personalTaskHolder = personalTaskHolderRepository.findOne(registrant);
        AchievementDiary diary = achievementDiaryRepository.findOne(registrant);
        return personalTaskHolder.getManualTaskList().stream()
                .filter(m -> m.getDeadline().before(minusDays(m.getDeadline(), daysBefore)))
                .filter(m -> !TaskUtils.isRated(m, diary.getManualAchievements()))
                .collect(Collectors.toList());
    }

    @Override
    public List<ManualTask> findUnratedManualTasks(Discipline discipline, boolean onlyResolved) {
        // fixme use java 8 stream api
        return Collections.emptyList();
    }

    @Override
    public List<QuestionsSuite> findUnresolvedQuestionsSuiteBeforeDeadline(Registrant registrant, int daysBeforeDeadline) {
        // fixme use java 8 stream api
        return Collections.emptyList();
    }

    @Override
    public void enableGroupTask(long id) {
        ManualTask manualTask = manualTaskRepository.findOne(id);
        if (manualTask.isEnabled()) {
            switchManualTaskState(manualTask, true);
        }
    }

    @Override
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
