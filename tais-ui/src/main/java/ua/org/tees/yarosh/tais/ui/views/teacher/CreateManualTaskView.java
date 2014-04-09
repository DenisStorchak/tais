package ua.org.tees.yarosh.tais.ui.views.teacher;

import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Qualifier;
import ua.org.tees.yarosh.tais.auth.annotations.PermitRoles;
import ua.org.tees.yarosh.tais.core.common.models.Discipline;
import ua.org.tees.yarosh.tais.core.common.models.StudentGroup;
import ua.org.tees.yarosh.tais.ui.components.layouts.DashPanel;
import ua.org.tees.yarosh.tais.ui.components.layouts.DashboardView;
import ua.org.tees.yarosh.tais.ui.core.mvp.PresentedBy;
import ua.org.tees.yarosh.tais.ui.core.mvp.TaisView;
import ua.org.tees.yarosh.tais.ui.views.teacher.api.CreateManualTaskTaisView;

import java.util.List;

import static ua.org.tees.yarosh.tais.core.common.dto.Roles.TEACHER;
import static ua.org.tees.yarosh.tais.ui.core.DataBinds.UriFragments.Teacher.ADD_MANUAL;
import static ua.org.tees.yarosh.tais.ui.core.VaadinUtils.createSingleFormLayout;
import static ua.org.tees.yarosh.tais.ui.views.teacher.api.CreateManualTaskTaisView.CreateManualTaskPresenter;

@PermitRoles(TEACHER)
@PresentedBy(CreateManualTaskPresenter.class)
@Qualifier(ADD_MANUAL)
@TaisView("Создать задание")
public class CreateManualTaskView extends DashboardView implements CreateManualTaskTaisView {

    private ComboBox groups = new ComboBox();
    private ComboBox disciplines = new ComboBox();
    private TextField description = new TextField();
    private Upload payload = new Upload();
    private DateField deadline = new DateField();
    private Button save = new Button();

    @Override
    public void setGroups(List<StudentGroup> studentGroups) {
        groups.removeAllItems();
        studentGroups.forEach(groups::addItem);
    }

    @Override
    public void setDisciplines(List<Discipline> disciplines) {
        this.disciplines.removeAllItems();
        disciplines.forEach(this.disciplines::addItem);
    }

    public CreateManualTaskView() {
        DashPanel dashPanel = addDashPanel(null, null);
        dashPanel.addComponent(createSingleFormLayout(new Label("Группа"), groups));
        dashPanel.addComponent(createSingleFormLayout(new Label("Дисциплина"), disciplines));
        dashPanel.addComponent(createSingleFormLayout(new Label("Задание"), payload));
        dashPanel.addComponent(createSingleFormLayout(new Label("Описание"), description));
        dashPanel.addComponent(createSingleFormLayout(new Label("Дедлайн"), deadline));
        dashPanel.addComponent(createSingleFormLayout(null, save));

        dashPanel.setSizeUndefined();
        dashPanel.setWidth(60, Unit.PERCENTAGE);
        dash.setComponentAlignment(dashPanel, Alignment.TOP_CENTER);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }
}
