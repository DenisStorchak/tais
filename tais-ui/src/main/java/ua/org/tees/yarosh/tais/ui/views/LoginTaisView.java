package ua.org.tees.yarosh.tais.ui.views;

import ua.org.tees.yarosh.tais.ui.core.mvp.Presenter;
import ua.org.tees.yarosh.tais.ui.core.mvp.PresenterBasedView;

public interface LoginTaisView extends PresenterBasedView<LoginTaisView.LoginPresenter> {

    interface LoginPresenter extends Presenter {
        boolean login(String username, String password);
    }
}
