package ua.org.tees.yarosh.tais.ui.core.mvp;

import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.VerticalLayout;

/**
 * @author Timur Yarosh
 *         Date: 23.03.14
 *         Time: 13:15
 */
public class PresenterBasedVerticalLayoutView<P extends Presenter> extends VerticalLayout
        implements PresenterBasedView<P> {

    private P presenter = null;

    @Override
    public void setPresenter(P presenter) {
        this.presenter = presenter;
    }

    @Override
    public P presenter() {
        return presenter;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        presenter().updateData();
    }
}
