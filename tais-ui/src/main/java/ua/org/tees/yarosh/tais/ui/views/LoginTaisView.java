package ua.org.tees.yarosh.tais.ui.views;

import ua.org.tees.yarosh.tais.ui.core.components.PresenterBasedView;
import ua.org.tees.yarosh.tais.ui.core.mvp.Presenter;

public interface LoginTaisView extends PresenterBasedView<LoginTaisView.LoginPresenter> {

    interface LoginPresenter extends Presenter {
    }
}
