package ua.org.tees.yarosh.tais.ui.views.admin;

import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import org.apache.commons.codec.digest.DigestUtils;
import ua.org.tees.yarosh.tais.core.common.dto.Position;
import ua.org.tees.yarosh.tais.core.common.models.Registrant;
import ua.org.tees.yarosh.tais.core.user.mgmt.api.service.RegistrantService;
import ua.org.tees.yarosh.tais.ui.core.HelpManager;
import ua.org.tees.yarosh.tais.ui.core.mvp.AbstractPresenter;
import ua.org.tees.yarosh.tais.ui.core.mvp.TaisView;

import static ua.org.tees.yarosh.tais.ui.configuration.ContextAccessor.getContext;
import static ua.org.tees.yarosh.tais.ui.configuration.ContextNames.WEB_CONTEXT;
import static ua.org.tees.yarosh.tais.ui.views.admin.UserRegistrationTaisView.UserRegistrationPresenter;

/**
 * @author Timur Yarosh
 *         Date: 22.03.14
 *         Time: 20:51
 */
public class UserRegistrationListener extends AbstractPresenter implements UserRegistrationPresenter {

    private RegistrantService registrantService = getContext(WEB_CONTEXT).getBean(RegistrantService.class);

    public void setRegistrantService(RegistrantService registrantService) {
        this.registrantService = registrantService;
    }

    public UserRegistrationListener(TaisView view, HelpManager helpManager) {
        super(view, helpManager);
    }

    @Override
    public boolean isLoginExists(String login) {
        return registrantService.loginExists(login);
    }

    @Override
    public boolean createRegistration(TextField login,
                                      PasswordField password,
                                      TextField name,
                                      TextField surname,
                                      TextField patronymic,
                                      TextField position,
                                      TextField studentGroup) {
        Registrant registrant = new Registrant();
        registrant.setLogin(login.getValue());
        registrant.setPassword(DigestUtils.sha256Hex(password.getValue()));
        registrant.setName(name.getValue());
        registrant.setSurname(surname.getValue());
        registrant.setPatronymic(patronymic.getValue());
        registrant.setPosition(Position.valueOf(position.getValue().toUpperCase()));
        return registrantService.createRegistration(registrant) != null;
    }
}
