package ua.org.tees.yarosh.tais.ui.views.student.api;

import com.vaadin.navigator.View;
import ua.org.tees.yarosh.tais.homework.models.ManualTask;
import ua.org.tees.yarosh.tais.homework.models.QuestionsSuite;
import ua.org.tees.yarosh.tais.ui.core.api.Updateable;
import ua.org.tees.yarosh.tais.ui.core.mvp.Presenter;

import java.util.List;

public interface UnresolvedTasksTaisView extends View, Updateable {

    void setUnresolvedManualTasks(List<ManualTask> manualTasks);

    void setUnresolvedQuestionsSuites(List<QuestionsSuite> questionsSuites);

    interface UnresolvedTasksPresenter extends Presenter {
        void onRefresh();

        void onQuestionsSuiteRequested(QuestionsSuite questionsSuite);

        void onQuestionsSuiteRun(QuestionsSuite questionsSuite);

        void onManualTaskRequested(ManualTask manualTask);

        void onManualTaskReported(ManualTask manualTask);
    }
}
