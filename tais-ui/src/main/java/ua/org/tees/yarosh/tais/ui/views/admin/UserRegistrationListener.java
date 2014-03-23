package ua.org.tees.yarosh.tais.ui.views.admin;

import com.vaadin.data.Validator;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.AbstractTextField;
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
import ua.org.tees.yarosh.tais.ui.configuration.SpringContextHelper;
import ua.org.tees.yarosh.tais.ui.core.HelpManager;
import ua.org.tees.yarosh.tais.ui.core.components.PresenterBasedView;
import ua.org.tees.yarosh.tais.ui.core.mvp.AbstractPresenter;

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
    public UserRegistrationListener(@Qualifier(USER_REGISTRATION) PresenterBasedView view,
                                    HelpManager helpManager,
                                    RegistrantService registrantService) {
        super(view, helpManager);
        this.registrantService = registrantService;
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
        fieldsValid(login, password, name, surname, patronymic);

        Registrant registrant = new Registrant();
        registrant.setLogin(login.getValue());
        registrant.setPassword(DigestUtils.sha256Hex(password.getValue()));
        registrant.setName(name.getValue());
        registrant.setSurname(surname.getValue());
        registrant.setPatronymic(patronymic.getValue());
        registrant.setRole(Role.valueOf(((String) position.getValue()).toUpperCase()));
        StudentGroup group = registrantService.findStudentGroup(Integer.valueOf(studentGroup.getValue().toString()));
        if (group == null) {
            group = new StudentGroup();
            group.setId(Integer.valueOf(studentGroup.getValue().toString()));
            group.setStudents(Arrays.asList(registrant));
        } else {
//            group.getStudents().add(registrant);
        }
        registrant.setGroup(group);
        return registrantService.createRegistration(registrant) != null;
    }

    @Override
    public List<String> listStudentGroups() {
        SpringContextHelper ctx = new SpringContextHelper(VaadinServlet.getCurrent().getServletContext());
        RegistrantService registrantService = ctx.getBean(RegistrantService.class);       // fixme ugly code
        return registrantService.listStudentGroups().stream().map(s -> s.getId().toString()).collect(toList());
    }

    @Override
    public List<String> listRoles() {
        return Arrays.asList(Role.GOD.toString(), Role.TEACHER.toString(), Role.STUDENT.toString());
    }

    @Override
    protected void initView(PresenterBasedView view) {
        ComboBox studentGroup = new ComboBox();
        listStudentGroups().forEach(studentGroup::addItem);
        getView(UserRegistrationView.class).setStudentGroupsComboBox(studentGroup);

        ComboBox roles = new ComboBox();
        listRoles().forEach(roles::addItem);
        getView(UserRegistrationView.class).setRolesComboBox(roles);
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
