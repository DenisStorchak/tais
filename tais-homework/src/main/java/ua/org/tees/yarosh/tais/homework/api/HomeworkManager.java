package ua.org.tees.yarosh.tais.homework.api;

import ua.org.tees.yarosh.tais.core.common.models.Discipline;
import ua.org.tees.yarosh.tais.core.common.models.Registrant;
import ua.org.tees.yarosh.tais.core.common.models.StudentGroup;
import ua.org.tees.yarosh.tais.homework.models.ManualTask;
import ua.org.tees.yarosh.tais.homework.models.ManualTaskResult;
import ua.org.tees.yarosh.tais.homework.models.QuestionsSuite;

import java.util.List;

public interface HomeworkManager {
    long createGeneralTask(ManualTask task);

    void enableGroupTask(long id);

    void disableGroupTask(long id);

    long createQuestionsSuite(QuestionsSuite questionsSuite);

    void enableQuestionsSuite(long id);

    void disableQuestionsSuite(long id);

    List<ManualTask> findManualTasks(StudentGroup studentGroup);

    List<QuestionsSuite> findQuestionsSuites(StudentGroup studentGroup);

    ManualTaskResult getManualTaskResult(Registrant registrant, ManualTask manualTask);

    void rate(ManualTaskResult manualTaskResult, Registrant examiner, int grade);

    List<ManualTask> findUnresolvedManualTasksBeforeDeadline(Registrant registrant, int daysBeforeDeadline);

    List<ManualTask> findUnratedManualTaskResults(Discipline discipline, boolean onlyResolved);

    List<QuestionsSuite> findUnresolvedQuestionsSuiteBeforeDeadline(Registrant registrant, int daysBeforeDeadline);
}
