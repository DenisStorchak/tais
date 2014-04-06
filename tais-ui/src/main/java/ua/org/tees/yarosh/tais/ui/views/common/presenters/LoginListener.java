package ua.org.tees.yarosh.tais.ui.views.common.presenters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import ua.org.tees.yarosh.tais.auth.AuthManager;
import ua.org.tees.yarosh.tais.ui.core.mvp.AbstractPresenter;
import ua.org.tees.yarosh.tais.ui.core.mvp.TaisPresenter;
import ua.org.tees.yarosh.tais.ui.core.mvp.UpdatableView;
import ua.org.tees.yarosh.tais.ui.views.common.api.LoginTaisView;

import static ua.org.tees.yarosh.tais.ui.core.DataBinds.UriFragments.AUTH;

@TaisPresenter
public class LoginListener extends AbstractPresenter implements LoginTaisView.LoginPresenter {

    @Autowired
    public LoginListener(@Qualifier(AUTH) UpdatableView view) {
        super(view);
    }

    @Override
    public boolean login(String username, String password) {
        return AuthManager.login(username, password);
    }
}
