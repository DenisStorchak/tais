package ua.org.tees.yarosh.tais.ui.student.views.api;

import com.vaadin.navigator.View;
import ua.org.tees.yarosh.tais.ui.core.mvp.AbstractPresenter;

public interface TaisView extends View {
    void addPresenter(AbstractPresenter presenter);
}
