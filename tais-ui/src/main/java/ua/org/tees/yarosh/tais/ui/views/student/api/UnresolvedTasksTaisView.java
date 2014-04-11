package ua.org.tees.yarosh.tais.ui.views.student.api;

import com.vaadin.navigator.View;
import ua.org.tees.yarosh.tais.homework.models.ManualTask;
import ua.org.tees.yarosh.tais.homework.models.QuestionsSuite;
import ua.org.tees.yarosh.tais.ui.core.api.Updatable;
import ua.org.tees.yarosh.tais.ui.core.mvp.Presenter;

import java.util.List;

public interface UnresolvedTasksTaisView extends View, Updatable {

    void setUnresolvedManualTasks(List<ManualTask> manualTasks);

    void setUnresolvedQuestionsSuites(List<QuestionsSuite> questionsSuites);

    interface UnresolvedTasksPresenter extends Presenter {
        void onUpdate();

        void onQuestionsSuite(QuestionsSuite questionsSuite);

        void onManualTask(ManualTask manualTask);
    }
}
