package ua.org.tees.yarosh.tais.ui.views.admin;

import com.vaadin.ui.ComboBox;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import ua.org.tees.yarosh.tais.ui.core.components.PresenterBasedView;
import ua.org.tees.yarosh.tais.ui.core.mvp.Presenter;

import java.util.List;

/**
 * @author Timur Yarosh
 *         Date: 22.03.14
 *         Time: 20:48
 */
public interface UserRegistrationTaisView extends PresenterBasedView<UserRegistrationTaisView.UserRegistrationPresenter> {
    interface UserRegistrationPresenter extends Presenter {
        boolean isLoginExists(String login);

        boolean createRegistration(TextField login,
                                   PasswordField password,
                                   TextField name,
                                   TextField surname,
                                   TextField patronymic,
                                   ComboBox position,
                                   ComboBox studentGroup);

        List<String> listStudentGroups();

        List<String> listRoles();
    }

    void setStudentGroupsComboBox(ComboBox studentGroupsComboBox);

    void setRolesComboBox(ComboBox rolesComboBox);
}
