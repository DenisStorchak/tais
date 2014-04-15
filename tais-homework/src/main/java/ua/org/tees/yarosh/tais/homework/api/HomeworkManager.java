package ua.org.tees.yarosh.tais.homework.api;

import ua.org.tees.yarosh.tais.core.common.models.Discipline;
import ua.org.tees.yarosh.tais.core.common.models.Registrant;
import ua.org.tees.yarosh.tais.core.common.models.StudentGroup;
import ua.org.tees.yarosh.tais.homework.models.ManualTask;
import ua.org.tees.yarosh.tais.homework.models.ManualTaskReport;
import ua.org.tees.yarosh.tais.homework.models.QuestionsSuite;

import java.util.List;

public interface HomeworkManager {
    long createManualTask(ManualTask task);

    void enableManualTask(long id);

    void disableManualTask(long id);

    long createQuestionsSuite(QuestionsSuite questionsSuite);

    QuestionsSuite findQuestionsSuite(long id);

    void enableQuestionsSuite(long id);

    void disableQuestionsSuite(long id);

    List<ManualTask> findManualTasks(StudentGroup studentGroup);

    List<QuestionsSuite> findQuestionsSuites(StudentGroup studentGroup);

    ManualTaskReport getManualTaskReport(Registrant registrant, ManualTask manualTask);

    void rate(ManualTaskReport manualTaskReport, Registrant examiner, int grade);

    List<ManualTask> findUnresolvedManualTasksBeforeDeadline(Registrant registrant, int daysBeforeDeadline);

    List<ManualTask> findUnresolvedActualManualTasks(Registrant registrant);

    List<ManualTaskReport> findUnratedManualTaskReports(Discipline discipline);

    List<QuestionsSuite> findUnresolvedQuestionsSuiteBeforeDeadline(Registrant registrant, int daysBeforeDeadline);

    List<QuestionsSuite> findUnresolvedActualQuestionsSuite(Registrant registrant);
}
