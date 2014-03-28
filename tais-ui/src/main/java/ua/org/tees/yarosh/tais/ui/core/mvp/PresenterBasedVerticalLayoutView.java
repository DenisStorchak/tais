package ua.org.tees.yarosh.tais.ui.core.mvp;

import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.VerticalLayout;

/**
 * @author Timur Yarosh
 *         Date: 23.03.14
 *         Time: 13:15
 */
public class PresenterBasedVerticalLayoutView extends VerticalLayout
        implements PresenterBasedView {

    private Presenter presenter = null;

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public Presenter presenter() {
        return presenter;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        presenter().updateData();
    }
}
