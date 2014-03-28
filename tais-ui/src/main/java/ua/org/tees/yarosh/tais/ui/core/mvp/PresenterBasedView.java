package ua.org.tees.yarosh.tais.ui.core.mvp;

import com.vaadin.navigator.View;

public interface PresenterBasedView<P extends Presenter> extends View {
    public void setPresenter(P presenter);

    public P presenter();
}