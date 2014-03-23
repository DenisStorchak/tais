package ua.org.tees.yarosh.tais.ui.core.components;

import com.vaadin.navigator.View;
import ua.org.tees.yarosh.tais.ui.core.mvp.AbstractPresenter;

import java.util.LinkedList;

public interface PresenterBasedView<P extends AbstractPresenter> extends View {
    public void addPresenter(P presenter);

    public LinkedList<P> getPresenters();

    public P primaryPresenter();

    public void setPrimaryPresenter(P presenter);
}
