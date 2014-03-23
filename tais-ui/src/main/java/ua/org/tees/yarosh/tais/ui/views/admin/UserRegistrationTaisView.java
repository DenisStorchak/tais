package ua.org.tees.yarosh.tais.ui.views.admin;

import com.vaadin.ui.ComboBox;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import ua.org.tees.yarosh.tais.ui.core.components.PresenterBasedView;

/**
 * @author Timur Yarosh
 *         Date: 22.03.14
 *         Time: 20:48
 */
public interface UserRegistrationTaisView extends PresenterBasedView<UserRegistrationListener> {
    interface UserRegistrationPresenter {
        boolean isLoginExists(String login);

        boolean createRegistration(TextField login,
                                   PasswordField password,
                                   TextField name,
                                   TextField surname,
                                   TextField patronymic,
                                   ComboBox position,
                                   TextField studentGroup);
    }
}
