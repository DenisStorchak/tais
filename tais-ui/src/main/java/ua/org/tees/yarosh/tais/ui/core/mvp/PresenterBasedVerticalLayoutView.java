package ua.org.tees.yarosh.tais.ui.core.mvp;

import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.VerticalLayout;
import ua.org.tees.yarosh.tais.ui.core.components.PresenterBasedView;

import java.util.LinkedList;

/**
 * @author Timur Yarosh
 *         Date: 23.03.14
 *         Time: 13:15
 */
public class PresenterBasedVerticalLayoutView<P extends Presenter> extends VerticalLayout
        implements PresenterBasedView<P> {

    private LinkedList<P> presenters = new LinkedList<>();

    @Override
    public void addPresenter(P presenter) {
        presenters.add(presenter);
    }

    @Override
    public LinkedList<P> getPresenters() {
        return presenters;
    }

    @Override
    public P primaryPresenter() {
        return presenters.getFirst();
    }

    @Override
    public void setPrimaryPresenter(P presenter) {
        presenters.addFirst(presenter);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        primaryPresenter().updateData();
    }
}
