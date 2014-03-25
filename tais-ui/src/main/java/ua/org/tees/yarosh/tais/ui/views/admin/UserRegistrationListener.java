package ua.org.tees.yarosh.tais.ui.views.admin;

import com.vaadin.ui.ComboBox;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import ua.org.tees.yarosh.tais.core.common.dto.Role;
import ua.org.tees.yarosh.tais.core.common.models.Registrant;
import ua.org.tees.yarosh.tais.core.common.models.StudentGroup;
import ua.org.tees.yarosh.tais.core.user.mgmt.api.service.RegistrantService;
import ua.org.tees.yarosh.tais.ui.core.HelpManager;
import ua.org.tees.yarosh.tais.ui.core.mvp.AbstractPresenter;
import ua.org.tees.yarosh.tais.ui.core.mvp.PresenterBasedView;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;
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
    public UserRegistrationListener(@Qualifier(USER_REGISTRATION) PresenterBasedView view,
                                    HelpManager helpManager) {
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
                                      ComboBox studentGroup) {
        Registrant registrant = new Registrant();
        registrant.setLogin(login.getValue());
        registrant.setPassword(DigestUtils.sha256Hex(password.getValue()));
        registrant.setName(name.getValue());
        registrant.setSurname(surname.getValue());
        registrant.setPatronymic(patronymic.getValue());
        registrant.setRole(Role.valueOf(((String) position.getValue()).toUpperCase()));
        StudentGroup group = registrantService.findStudentGroup(studentGroup.getValue().toString());
        registrant.setGroup(group);
        return registrantService.createRegistration(registrant) != null;
    }

    @Override
    public List<String> listStudentGroups() {
        return registrantService.listStudentGroups().stream().map(s -> s.getId().toString()).collect(toList());
    }

    @Override
    public List<String> listRoles() {
        return Arrays.asList(Role.GOD.toString(), Role.TEACHER.toString(), Role.STUDENT.toString());
    }

    @Override
    public void updateData() {
        ComboBox studentGroup = new ComboBox();
        listStudentGroups().forEach(studentGroup::addItem);
        getView(UserRegistrationView.class).setStudentGroupsComboBox(studentGroup);

        ComboBox roles = new ComboBox();
        listRoles().forEach(roles::addItem);
        getView(UserRegistrationView.class).setRolesComboBox(roles);
    }
}
