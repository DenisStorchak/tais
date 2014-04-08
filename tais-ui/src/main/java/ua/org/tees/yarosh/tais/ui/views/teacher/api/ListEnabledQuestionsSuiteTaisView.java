package ua.org.tees.yarosh.tais.ui.views.teacher.api;

import com.vaadin.data.Container;
import com.vaadin.navigator.View;
import ua.org.tees.yarosh.tais.ui.core.api.UpdatableView;
import ua.org.tees.yarosh.tais.ui.core.mvp.Presenter;

public interface ListEnabledQuestionsSuiteTaisView extends View, UpdatableView {
    interface ListEnabledQuestionsSuitePresenter extends Presenter {
        Container suitesContainer();
    }
}
