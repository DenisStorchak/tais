package ua.org.tees.yarosh.tais.ui.core.mvp;

import com.vaadin.navigator.View;

public interface TaisView extends View {
    void addPresenter(AbstractPresenter presenter);
}
