package ua.org.tees.yarosh.tais.homework.api;

import ua.org.tees.yarosh.tais.core.common.models.Discipline;
import ua.org.tees.yarosh.tais.core.common.models.Registrant;
import ua.org.tees.yarosh.tais.core.common.models.StudentGroup;
import ua.org.tees.yarosh.tais.homework.events.ManualTaskEnabledEvent;
import ua.org.tees.yarosh.tais.homework.models.ManualTask;
import ua.org.tees.yarosh.tais.homework.models.ManualTaskReport;
import ua.org.tees.yarosh.tais.homework.models.QuestionsSuite;

import java.util.List;

public interface HomeworkManager {
    long createManualTask(ManualTask task);

    void enableManualTask(long id);

    void addManualTaskEnabledListener(ManualTaskEnabledListenerTeacher listener);

    void disableManualTask(long id);

    void addManualTaskDisabledListener(ManualTaskDisabledListenerTeacher listener);

    long createQuestionsSuite(QuestionsSuite questionsSuite);

    QuestionsSuite findQuestionsSuite(long id);

    void enableQuestionsSuite(long id);

    void addQuestionsSuiteEnabledListener(QuestionsSuiteEnabledListenerTeacher listener);

    void disableQuestionsSuite(long id);

    void addQuestionsSuiteDisabledListener(QuestionsSuiteDisabledListenerTeacher listener);

    List<ManualTask> findManualTasks(StudentGroup studentGroup);

    List<QuestionsSuite> findQuestionsSuites(StudentGroup studentGroup);

    ManualTaskReport getManualTaskReport(Registrant registrant, ManualTask manualTask);

    void rate(ManualTaskReport manualTaskReport, Registrant examiner, int grade);

    void addManualTaskRatedListener(ManualTaskRatedListenerTeacher listener);

    List<ManualTask> findUnresolvedManualTasksBeforeDeadline(Registrant registrant, int daysBeforeDeadline);

    List<ManualTask> findUnresolvedActualManualTasks(Registrant registrant);

    List<ManualTaskReport> findUnratedManualTaskReports(Discipline discipline);

    List<QuestionsSuite> findUnresolvedQuestionsSuiteBeforeDeadline(Registrant registrant, int daysBeforeDeadline);

    List<QuestionsSuite> findUnresolvedActualQuestionsSuite(Registrant registrant);

    interface TeacherEventListener {
    }

    interface ManualTaskEnabledListenerTeacher extends TeacherEventListener {
        void onEnabled(ManualTaskEnabledEvent event);
    }

    interface ManualTaskDisabledListenerTeacher extends TeacherEventListener {
        void onDisabled(ManualTask manualTask);
    }

    interface QuestionsSuiteEnabledListenerTeacher extends TeacherEventListener {
        void onEnabled(QuestionsSuite questionsSuite);
    }

    interface QuestionsSuiteDisabledListenerTeacher extends TeacherEventListener {
        void onDisabled(QuestionsSuite questionsSuite);
    }

    interface ManualTaskRatedListenerTeacher extends TeacherEventListener {
        void onRated(ManualTaskReport manualTaskReport, Registrant examiner, int grade);
    }
}
