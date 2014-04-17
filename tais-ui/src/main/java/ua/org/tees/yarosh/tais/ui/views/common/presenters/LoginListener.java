package ua.org.tees.yarosh.tais.ui.views.common.presenters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import ua.org.tees.yarosh.tais.auth.AuthManager;
import ua.org.tees.yarosh.tais.core.common.models.Registrant;
import ua.org.tees.yarosh.tais.core.common.properties.DefaultUserProperties;
import ua.org.tees.yarosh.tais.core.user.mgmt.api.service.RegistrantService;
import ua.org.tees.yarosh.tais.ui.core.UIFactoryAccessor;
import ua.org.tees.yarosh.tais.ui.core.api.Updateable;
import ua.org.tees.yarosh.tais.ui.core.events.LoginEvent;
import ua.org.tees.yarosh.tais.ui.core.mvp.AbstractPresenter;
import ua.org.tees.yarosh.tais.ui.core.mvp.TaisPresenter;

import static ua.org.tees.yarosh.tais.ui.core.api.DataBinds.UriFragments.AUTH;
import static ua.org.tees.yarosh.tais.ui.views.common.api.LoginTais.LoginPresenter;

@TaisPresenter
public class LoginListener extends AbstractPresenter implements LoginPresenter {

    private RegistrantService registrantService;
    private DefaultUserProperties defaultUserProperties;
    private AuthManager authManager;

    @Autowired
    public void setRegistrantService(RegistrantService registrantService) {
        this.registrantService = registrantService;
    }

    @Autowired
    public void setDefaultUserProperties(DefaultUserProperties defaultUserProperties) {
        this.defaultUserProperties = defaultUserProperties;
    }

    @Autowired
    public void setAuthManager(AuthManager authManager) {
        this.authManager = authManager;
    }

    @Autowired
    public LoginListener(@Qualifier(AUTH) Updateable view) {
        super(view);
    }

    @Override
    public Registrant login(String username, String password) {
        if (authManager.login(username, password)) {

            Registrant registration = registrantService.getRegistration(username);
            if (registration == null) { // user in memory
                registration = new Registrant();
                registration.setLogin(defaultUserProperties.getLogin());
                registration.setRole(defaultUserProperties.getRole());
            }
            UIFactoryAccessor.getCurrent().getEventBus().post(new LoginEvent(registration));
            return registration;
        }
        return null;
    }
}
