package ua.org.tees.yarosh.tais.ui.views;

import ua.org.tees.yarosh.tais.ui.core.mvp.Presenter;

public interface LoginTaisView {

    interface LoginPresenter extends Presenter {
        boolean login(String username, String password);
    }
}
