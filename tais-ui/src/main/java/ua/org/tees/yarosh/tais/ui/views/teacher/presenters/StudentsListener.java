package ua.org.tees.yarosh.tais.ui.views.teacher.presenters;

import com.vaadin.ui.UI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import ua.org.tees.yarosh.tais.core.common.models.Registrant;
import ua.org.tees.yarosh.tais.core.user.mgmt.api.service.RegistrantService;
import ua.org.tees.yarosh.tais.ui.components.windows.RegistrantDetailWindow;
import ua.org.tees.yarosh.tais.ui.components.windows.SendEmailWindow;
import ua.org.tees.yarosh.tais.ui.core.UIFactoryAccessor;
import ua.org.tees.yarosh.tais.ui.core.api.Updateable;
import ua.org.tees.yarosh.tais.ui.core.mvp.AbstractPresenter;
import ua.org.tees.yarosh.tais.ui.core.mvp.TaisPresenter;
import ua.org.tees.yarosh.tais.ui.views.teacher.api.StudentsTaisView;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static ua.org.tees.yarosh.tais.core.common.dto.Roles.STUDENT;
import static ua.org.tees.yarosh.tais.ui.core.DataBinds.UriFragments.Teacher.STUDENTS;
import static ua.org.tees.yarosh.tais.ui.views.teacher.api.StudentsTaisView.StudentsTaisPresenter;

@TaisPresenter
public class StudentsListener extends AbstractPresenter implements StudentsTaisPresenter {

    private RegistrantService registrantService;

    @Autowired
    public void setRegistrantService(RegistrantService registrantService) {
        this.registrantService = registrantService;
    }

    @Autowired
    public StudentsListener(@Qualifier(STUDENTS) Updateable view) {
        super(view);
    }

    @Override
    public void init() {
        List<Registrant> students = registrantService.findAllRegistrants().stream()
                .filter(r -> r.getRole().equals(STUDENT)).collect(toList());
        getView(StudentsTaisView.class).setStudents(students);
    }

    @Override
    public void onDetails(Registrant registrant) {
        RegistrantDetailWindow window = UIFactoryAccessor.getCurrent().getWindow(RegistrantDetailWindow.class);
        window.setRegistrant(registrant);
        UI.getCurrent().addWindow(window);
    }

    @Override
    public void onSend(Registrant registrant) {
        SendEmailWindow window = UIFactoryAccessor.getCurrent().getWindow(SendEmailWindow.class);
        UI.getCurrent().addWindow(window);
    }
}
