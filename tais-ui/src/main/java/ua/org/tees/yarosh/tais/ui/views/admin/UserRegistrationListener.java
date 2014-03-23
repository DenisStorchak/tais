package ua.org.tees.yarosh.tais.ui.views.admin;

import com.vaadin.data.Validator;
import com.vaadin.ui.AbstractTextField;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import ua.org.tees.yarosh.tais.core.common.dto.Position;
import ua.org.tees.yarosh.tais.core.common.models.Registrant;
import ua.org.tees.yarosh.tais.core.common.models.StudentGroup;
import ua.org.tees.yarosh.tais.core.user.mgmt.api.service.RegistrantService;
import ua.org.tees.yarosh.tais.ui.core.HelpManager;
import ua.org.tees.yarosh.tais.ui.core.components.PresenterBasedView;
import ua.org.tees.yarosh.tais.ui.core.mvp.AbstractPresenter;

import static ua.org.tees.yarosh.tais.ui.core.UriFragments.Admin.USER_REGISTRATION;
import static ua.org.tees.yarosh.tais.ui.views.admin.UserRegistrationTaisView.UserRegistrationPresenter;

/**
 * @author Timur Yarosh
 *         Date: 22.03.14
 *         Time: 20:51
 */
@Service
@Scope("prototype")
public class UserRegistrationListener extends AbstractPresenter implements UserRegistrationPresenter {

    private RegistrantService registrantService;

    @Autowired
    public void setRegistrantService(RegistrantService registrantService) {
        this.registrantService = registrantService;
    }

    @Autowired
    public UserRegistrationListener(@Qualifier(USER_REGISTRATION) PresenterBasedView view, HelpManager helpManager) {
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
                                      ComboBox position,
                                      TextField studentGroup) {
        fieldsValid(login, password, name, surname, patronymic, studentGroup);

        Registrant registrant = new Registrant();
        registrant.setLogin(login.getValue());
        registrant.setPassword(DigestUtils.sha256Hex(password.getValue()));
        registrant.setName(name.getValue());
        registrant.setSurname(surname.getValue());
        registrant.setPatronymic(patronymic.getValue());
        registrant.setPosition(Position.valueOf(((String) position.getValue()).

                        toUpperCase()
        ));
        StudentGroup group = new StudentGroup();
        group.setId(Integer.valueOf(studentGroup.getValue()));
        registrant.setGroup(group);
        return registrantService.createRegistration(registrant) != null;
    }

    private void fieldsValid(AbstractTextField... fields) {
        for (AbstractTextField field : fields) {
            try {
                field.validate();
            } catch (Validator.InvalidValueException e) {
                field.focus();
                throw e;
            }
        }
    }
}
