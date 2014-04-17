package ua.org.tees.yarosh.tais.ui.views.teacher;

import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
import org.springframework.beans.factory.annotation.Qualifier;
import ua.org.tees.yarosh.tais.auth.annotations.PermitRoles;
import ua.org.tees.yarosh.tais.core.common.models.Registrant;
import ua.org.tees.yarosh.tais.ui.components.PlainBorderlessTable;
import ua.org.tees.yarosh.tais.ui.components.layouts.DashboardView;
import ua.org.tees.yarosh.tais.ui.core.UIFactoryAccessor;
import ua.org.tees.yarosh.tais.ui.core.mvp.PresentedBy;
import ua.org.tees.yarosh.tais.ui.core.mvp.TaisView;
import ua.org.tees.yarosh.tais.ui.views.teacher.api.StudentsTaisView;

import java.util.List;

import static ua.org.tees.yarosh.tais.core.common.dto.Roles.TEACHER;
import static ua.org.tees.yarosh.tais.ui.core.DataBinds.UriFragments.Teacher.STUDENTS;
import static ua.org.tees.yarosh.tais.ui.views.teacher.api.StudentsTaisView.StudentsTaisPresenter;

@PresentedBy(StudentsTaisPresenter.class)
@Qualifier(STUDENTS)
@PermitRoles(TEACHER)
@TaisView("Студенты")
public class StudentsView extends DashboardView implements StudentsTaisView {

    private static final String PROPERTY_LOGIN = "Логин";
    private static final String PROPERTY_SURNAME = "Фамилия";
    private static final String PROPERTY_NAME = "Имя";
    private static final String PROPERTY_PATRONYMIC = "Отчество";
    private static final String PROPERTY_EMAIL = "Email";
    private static final String PROPERTY_GROUP = "Группа";
    private static final String PROPERTY_INTERACTION = "Взаимодействие";
    private Table students = new PlainBorderlessTable("Все студенты");

    public StudentsView() {
        addDashPanel(null, null, students);
    }

    @Override
    public void setStudents(List<Registrant> registrants) {
        Container container = new IndexedContainer();
        container.addContainerProperty(PROPERTY_LOGIN, String.class, null);
        container.addContainerProperty(PROPERTY_SURNAME, String.class, null);
        container.addContainerProperty(PROPERTY_NAME, String.class, null);
        container.addContainerProperty(PROPERTY_PATRONYMIC, String.class, null);
        container.addContainerProperty(PROPERTY_EMAIL, String.class, null);
        container.addContainerProperty(PROPERTY_GROUP, String.class, null);
        container.addContainerProperty(PROPERTY_INTERACTION, HorizontalLayout.class, null);

        for (Registrant registrant : registrants) {
            Item item = container.addItem(registrant.getLogin());
            item.getItemProperty(PROPERTY_LOGIN).setValue(registrant.getLogin());
            item.getItemProperty(PROPERTY_SURNAME).setValue(registrant.getSurname());
            item.getItemProperty(PROPERTY_NAME).setValue(registrant.getName());
            item.getItemProperty(PROPERTY_PATRONYMIC).setValue(registrant.getPatronymic());
            item.getItemProperty(PROPERTY_EMAIL).setValue(registrant.getEmail());
            item.getItemProperty(PROPERTY_GROUP).setValue(registrant.getGroup().toString());
            item.getItemProperty(PROPERTY_INTERACTION).setValue(createInteractionLayout(registrant));
        }

        students.setContainerDataSource(container);
    }

    private HorizontalLayout createInteractionLayout(Registrant registrant) {
        Button details = new Button("Детали");
        details.addStyleName("icon-only");
        details.addStyleName("icon-logout");
        details.setDescription("Открыть детали");
        details.addClickListener(event -> UIFactoryAccessor.getCurrent()
                .getRelativePresenter(this, StudentsTaisPresenter.class)
                .onDetails(registrant));

        Button send = new Button("Отправить email");
        send.addStyleName("icon-only");
        send.addStyleName("icon-logout");
        send.setDescription("Отправить email");
        send.addClickListener(event -> UIFactoryAccessor.getCurrent()
                .getRelativePresenter(this, StudentsTaisPresenter.class)
                .onSend(registrant));

        return new HorizontalLayout(details, send);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }
}
