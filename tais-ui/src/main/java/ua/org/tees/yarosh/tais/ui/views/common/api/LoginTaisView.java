package ua.org.tees.yarosh.tais.ui.views.common.api;

import com.vaadin.navigator.View;
import ua.org.tees.yarosh.tais.ui.core.mvp.Presenter;
import ua.org.tees.yarosh.tais.ui.core.mvp.UpdatableView;

public interface LoginTaisView extends View, UpdatableView {

    interface LoginPresenter extends Presenter {
        boolean login(String username, String password);
    }
}
