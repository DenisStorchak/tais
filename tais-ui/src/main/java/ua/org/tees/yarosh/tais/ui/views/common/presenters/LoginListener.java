package ua.org.tees.yarosh.tais.ui.views.common.presenters;

import com.vaadin.server.VaadinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import ua.org.tees.yarosh.tais.auth.AuthManager;
import ua.org.tees.yarosh.tais.core.common.models.Registrant;
import ua.org.tees.yarosh.tais.core.common.properties.DefaultUserProperties;
import ua.org.tees.yarosh.tais.core.user.mgmt.api.service.RegistrantService;
import ua.org.tees.yarosh.tais.ui.core.DataBinds;
import ua.org.tees.yarosh.tais.ui.core.mvp.AbstractPresenter;
import ua.org.tees.yarosh.tais.ui.core.mvp.TaisPresenter;
import ua.org.tees.yarosh.tais.ui.core.mvp.UpdatableView;

import javax.servlet.http.Cookie;

import static ua.org.tees.yarosh.tais.ui.core.DataBinds.UriFragments.AUTH;
import static ua.org.tees.yarosh.tais.ui.views.common.api.LoginTaisView.LoginPresenter;

@TaisPresenter
public class LoginListener extends AbstractPresenter implements LoginPresenter {

    private RegistrantService registrantService;
    private DefaultUserProperties defaultUserProperties;

    @Autowired
    public void setRegistrantService(RegistrantService registrantService) {
        this.registrantService = registrantService;
    }

    @Autowired
    public void setDefaultUserProperties(DefaultUserProperties defaultUserProperties) {
        this.defaultUserProperties = defaultUserProperties;
    }

    @Autowired
    public LoginListener(@Qualifier(AUTH) UpdatableView view) {
        super(view);
    }

    @Override
    public Registrant login(String username, String password) {
        if (AuthManager.login(username, password)) {

            Cookie cookie = new Cookie(DataBinds.Cookies.AUTH, username);
            cookie.setMaxAge(15552000);
            cookie.setPath("/");
            VaadinService.getCurrentResponse().addCookie(cookie);

            Registrant registration = registrantService.getRegistration(username);
            if (registration == null) { // user in memory
                registration = new Registrant();
                registration.setLogin(defaultUserProperties.getLogin());
                registration.setRole(defaultUserProperties.getRole());
            }
            return registration;
        }
        return null;
    }
}
