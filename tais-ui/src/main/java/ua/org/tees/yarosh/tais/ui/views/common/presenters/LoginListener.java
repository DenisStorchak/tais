package ua.org.tees.yarosh.tais.ui.views.common.presenters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import ua.org.tees.yarosh.tais.auth.AuthManager;
import ua.org.tees.yarosh.tais.ui.core.mvp.AbstractPresenter;
import ua.org.tees.yarosh.tais.ui.core.mvp.UpdatableView;
import ua.org.tees.yarosh.tais.ui.views.common.api.LoginTaisView;

import static ua.org.tees.yarosh.tais.ui.core.text.UriFragments.AUTH;

@Service
@Scope("prototype")
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
