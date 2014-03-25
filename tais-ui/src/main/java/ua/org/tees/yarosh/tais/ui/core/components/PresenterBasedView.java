package ua.org.tees.yarosh.tais.ui.core.components;

import com.vaadin.navigator.View;
import ua.org.tees.yarosh.tais.ui.core.mvp.Presenter;

import java.util.LinkedList;

public interface PresenterBasedView<P extends Presenter> extends View {
    public void addPresenter(P presenter);

    public LinkedList<P> getPresenters();

    public P primaryPresenter();

    public void setPrimaryPresenter(P presenter);
}
