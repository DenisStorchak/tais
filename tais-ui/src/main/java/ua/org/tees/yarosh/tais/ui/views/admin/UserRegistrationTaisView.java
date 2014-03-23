package ua.org.tees.yarosh.tais.ui.views.admin;

import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import ua.org.tees.yarosh.tais.ui.core.mvp.TaisView;

/**
 * @author Timur Yarosh
 *         Date: 22.03.14
 *         Time: 20:48
 */
public interface UserRegistrationTaisView extends TaisView {
    interface UserRegistrationPresenter {
        boolean isLoginExists(String login);

        boolean createRegistration(TextField login,
                                   PasswordField password,
                                   TextField textField, TextField name,
                                   TextField surname,
                                   TextField patronymic,
                                   TextField position);
    }
}
