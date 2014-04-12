package ua.org.tees.yarosh.tais.ui.views.admin.presenters;

import com.vaadin.ui.ComboBox;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import ua.org.tees.yarosh.tais.core.common.models.Discipline;
import ua.org.tees.yarosh.tais.core.common.models.Registrant;
import ua.org.tees.yarosh.tais.core.common.models.StudentGroup;
import ua.org.tees.yarosh.tais.core.user.mgmt.api.service.RegistrantService;
import ua.org.tees.yarosh.tais.schedule.api.ClassroomService;
import ua.org.tees.yarosh.tais.schedule.api.DisciplineService;
import ua.org.tees.yarosh.tais.schedule.models.Classroom;
import ua.org.tees.yarosh.tais.ui.core.api.Updateable;
import ua.org.tees.yarosh.tais.ui.core.mvp.AbstractPresenter;
import ua.org.tees.yarosh.tais.ui.core.mvp.TaisPresenter;
import ua.org.tees.yarosh.tais.ui.views.admin.UserRegistrationView;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static ua.org.tees.yarosh.tais.core.common.dto.Roles.*;
import static ua.org.tees.yarosh.tais.ui.RoleTranslator.translate;
import static ua.org.tees.yarosh.tais.ui.core.DataBinds.UriFragments.Admin.USER_REGISTRATION;
import static ua.org.tees.yarosh.tais.ui.views.admin.api.UserRegistrationTaisView.UserRegistrationPresenter;

/**
 * @author Timur Yarosh
 *         Date: 22.03.14
 *         Time: 20:51
 */
@TaisPresenter
public class UserRegistrationListener extends AbstractPresenter implements UserRegistrationPresenter {

    private RegistrantService registrantService;
    private DisciplineService disciplineService;
    private ClassroomService classroomService;

    @Autowired
    public void setRegistrantService(RegistrantService registrantService) {
        this.registrantService = registrantService;
    }

    @Autowired
    public void setDisciplineService(DisciplineService disciplineService) {
        this.disciplineService = disciplineService;
    }

    @Autowired
    public void setClassroomService(ClassroomService classroomService) {
        this.classroomService = classroomService;
    }

    @Autowired
    public UserRegistrationListener(@Qualifier(USER_REGISTRATION) Updateable view) {
        super(view);
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
                                      TextField email,
                                      ComboBox position,
                                      ComboBox studentGroup) {
        Registrant registrant = new Registrant();
        registrant.setLogin(login.getValue());
        registrant.setPassword(DigestUtils.sha256Hex(password.getValue()));
        registrant.setName(name.getValue());
        registrant.setSurname(surname.getValue());
        registrant.setEmail(email.getValue());
        registrant.setPatronymic(patronymic.getValue());
        registrant.setRole(translate((String) position.getValue()));
        StudentGroup group = registrantService.findStudentGroup(studentGroup.getValue().toString());
        registrant.setGroup(group);
        return registrantService.createRegistration(registrant) != null;
    }

    @Override
    public boolean createClassroom(String classroom) {
        return classroomService.createClassroom(new Classroom(classroom)) != null;
    }

    @Override
    public boolean createDiscipline(String discipline) {
        return disciplineService.createDiscipline(new Discipline(discipline)) != null;
    }

    @Override
    public List<String> listStudentGroups() {
        List<StudentGroup> studentGroups = registrantService.findAllStudentGroups();
        return studentGroups.stream().map(StudentGroup::getId).collect(toList());
    }

    @Override
    public List<String> listRoles() {
        return Arrays.asList(
                translate(ADMIN),
                translate(TEACHER),
                translate(STUDENT));
    }

    @Override
    public void update() {
        getView(UserRegistrationView.class).update();
    }
}
