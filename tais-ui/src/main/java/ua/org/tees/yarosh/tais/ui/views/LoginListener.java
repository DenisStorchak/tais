package ua.org.tees.yarosh.tais.ui.views;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import ua.org.tees.yarosh.tais.ui.core.HelpManager;
import ua.org.tees.yarosh.tais.ui.core.mvp.AbstractPresenter;
import ua.org.tees.yarosh.tais.ui.core.mvp.PresenterBasedView;

import static ua.org.tees.yarosh.tais.ui.core.UriFragments.AUTH;
import static ua.org.tees.yarosh.tais.ui.views.LoginTaisView.LoginPresenter;

@Service
@Scope("prototype")
public class LoginListener extends AbstractPresenter implements LoginPresenter {
    @Autowired
    public LoginListener(@Qualifier(AUTH) PresenterBasedView view, HelpManager helpManager) {
        super(view, helpManager);
    }
}
