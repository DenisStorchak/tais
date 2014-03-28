package ua.org.tees.yarosh.tais.ui.views.admin;

import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import ua.org.tees.yarosh.tais.auth.annotations.PermitRoles;
import ua.org.tees.yarosh.tais.ui.components.BgPanel;
import ua.org.tees.yarosh.tais.ui.components.Dash;
import ua.org.tees.yarosh.tais.ui.components.windows.CreateLessonWindow;
import ua.org.tees.yarosh.tais.ui.core.mvp.PresenterBasedVerticalLayoutView;
import ua.org.tees.yarosh.tais.ui.core.mvp.ProducedBy;

import java.util.List;

import static ua.org.tees.yarosh.tais.core.common.dto.Role.ADMIN;
import static ua.org.tees.yarosh.tais.ui.core.text.UriFragments.Admin.CREATE_SCHEDULE;
import static ua.org.tees.yarosh.tais.ui.views.admin.CreateScheduleTaisView.CreateSchedulePresenter;

@ProducedBy(CreateScheduleListener.class)
@Service
@Qualifier(CREATE_SCHEDULE)
@Scope("prototype")
@PermitRoles(ADMIN)
public class CreateScheduleView extends PresenterBasedVerticalLayoutView<CreateSchedulePresenter>
        implements CreateScheduleTaisView {

    private ComboBox groups = new ComboBox();
    private Button createDayScheduleButton = new Button("Добавить пару");
    private DateField periodFrom = new DateField();
    private DateField periodTo = new DateField();

    public CreateScheduleView() {
        createDayScheduleButton.setDescription("Добавить расписание на 1 день");
        createDayScheduleButton.addClickListener(event -> getUI().addWindow(new CreateLessonWindow()));
        setSizeFull();
        addStyleName("dashboard-view");
        HorizontalLayout top = new BgPanel("Создание расписания");
        addComponent(top);

        HorizontalLayout dash = new Dash();
        addComponent(dash);
        setExpandRatio(dash, 1.5f);

        HorizontalLayout controls = new HorizontalLayout();
        controls.setSizeUndefined();
        controls.addComponents(groups, periodFrom, periodTo, createDayScheduleButton);

        dash.addComponent(controls);
    }

    @Override
    public void setGroups(List<String> groups) {
        this.groups.removeAllItems();
        groups.forEach(this.groups::addItem);
    }
}
