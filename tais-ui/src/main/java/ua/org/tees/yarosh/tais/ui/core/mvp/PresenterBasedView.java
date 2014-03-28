package ua.org.tees.yarosh.tais.ui.core.mvp;

import com.vaadin.navigator.View;

public interface PresenterBasedView extends View {
    public void setPresenter(Presenter presenter);

    public Presenter presenter();
}