package ua.org.tees.yarosh.tais.ui.views.admin.api;

import com.vaadin.navigator.View;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import ua.org.tees.yarosh.tais.ui.core.api.Updatable;
import ua.org.tees.yarosh.tais.ui.core.mvp.Presenter;

import java.util.List;

/**
 * @author Timur Yarosh
 *         Date: 22.03.14
 *         Time: 20:48
 */
public interface UserRegistrationTais extends View, Updatable {
    interface UserRegistrationPresenter extends Presenter {
        boolean isLoginExists(String login);

        boolean createRegistration(TextField login,
                                   PasswordField password,
                                   TextField name,
                                   TextField surname,
                                   TextField patronymic,
                                   TextField email,
                                   ComboBox position,
                                   ComboBox studentGroup);

        boolean createClassroom(String classroom);

        boolean createDiscipline(String discipline);

        List<String> listStudentGroups();

        List<String> listRoles();
    }
}
