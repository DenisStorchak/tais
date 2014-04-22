package ua.org.tees.yarosh.tais.ui.views.teacher;

import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Qualifier;
import ua.org.tees.yarosh.tais.auth.api.annotations.PermitRoles;
import ua.org.tees.yarosh.tais.core.common.models.Discipline;
import ua.org.tees.yarosh.tais.core.common.models.StudentGroup;
import ua.org.tees.yarosh.tais.homework.models.ManualTask;
import ua.org.tees.yarosh.tais.ui.components.PayloadReceiver;
import ua.org.tees.yarosh.tais.ui.components.layouts.DashPanel;
import ua.org.tees.yarosh.tais.ui.components.layouts.DashboardView;
import ua.org.tees.yarosh.tais.ui.core.api.Registrants;
import ua.org.tees.yarosh.tais.ui.core.mvp.PresentedBy;
import ua.org.tees.yarosh.tais.ui.core.mvp.TaisView;
import ua.org.tees.yarosh.tais.ui.views.teacher.api.CreateManualTaskTaisView;

import java.util.Date;
import java.util.List;

import static ua.org.tees.yarosh.tais.core.common.dto.Roles.TEACHER;
import static ua.org.tees.yarosh.tais.ui.core.UIFactory.getCurrent;
import static ua.org.tees.yarosh.tais.ui.core.VaadinUtils.createSingleFormLayout;
import static ua.org.tees.yarosh.tais.ui.core.VaadinUtils.isValid;
import static ua.org.tees.yarosh.tais.ui.core.api.DataBinds.UriFragments.Teacher.ADD_MANUAL;
import static ua.org.tees.yarosh.tais.ui.views.teacher.api.CreateManualTaskTaisView.CreateManualTaskPresenter;

@PermitRoles(TEACHER)
@PresentedBy(CreateManualTaskPresenter.class)
@Qualifier(ADD_MANUAL)
@TaisView("Создать задание")
public class CreateManualTaskView extends DashboardView implements CreateManualTaskTaisView {

    private ComboBox groups = new ComboBox();
    private ComboBox disciplines = new ComboBox();
    private TextArea description = new TextArea();
    private Upload payloadUploader = new Upload();
    private DateField deadline = new DateField();
    private TextField theme = new TextField();
    private Button save = new Button("Добавить");

    private String payloadPath;

    @Override
    public void setGroups(List<StudentGroup> studentGroups) {
        groups.removeAllItems();
        studentGroups.forEach(groups::addItem);
    }

    @Override
    public void init() {
        CreateManualTaskPresenter p = getCurrent().getRelativePresenter(this, CreateManualTaskPresenter.class);
        save.addClickListener(e -> {
            if (isValid(groups, disciplines, description, deadline, theme) && payloadPath != null) {
                ManualTask manualTask = new ManualTask();
                manualTask.setStudentGroup((StudentGroup) groups.getValue());
                manualTask.setDiscipline((Discipline) disciplines.getValue());
                manualTask.setDeadline(deadline.getValue());
                manualTask.setDescription(description.getValue());
                manualTask.setEnabled(true);
                manualTask.setExaminer(Registrants.getCurrent());
                manualTask.setPayloadPath(payloadPath);
                manualTask.setTheme(theme.getValue());
                manualTask.setTimestamp(new Date());
                p.onCreate(manualTask);
            }
        });
    }

    @Override
    public void setDisciplines(List<Discipline> disciplines) {
        this.disciplines.removeAllItems();
        disciplines.forEach(this.disciplines::addItem);
    }

    @Override
    public void setPayloadReceiver(PayloadReceiver payloadReceiver) {
        payloadUploader.setReceiver(payloadReceiver);
        payloadUploader.addFailedListener(payloadReceiver);
        payloadUploader.addSucceededListener(payloadReceiver);
    }

    @Override
    public void setPayloadPath(String path) {
        payloadPath = path;
    }

    public CreateManualTaskView() {
        top.addComponent(payloadUploader);

        DashPanel dashPanel = addDashPanel(null, null);
        dashPanel.addComponent(createSingleFormLayout(new Label("Группа"), groups));
        dashPanel.addComponent(createSingleFormLayout(new Label("Дисциплина"), disciplines));
        dashPanel.addComponent(createSingleFormLayout(new Label("Тема"), theme));
        dashPanel.addComponent(createSingleFormLayout(new Label("Описание"), description));
        dashPanel.addComponent(createSingleFormLayout(new Label("Дедлайн"), deadline));
        dashPanel.addComponent(createSingleFormLayout(null, save));

        dashPanel.setSizeUndefined();
        dashPanel.setWidth(80, Unit.PERCENTAGE);
        dash.setComponentAlignment(dashPanel, Alignment.TOP_CENTER);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }
}
