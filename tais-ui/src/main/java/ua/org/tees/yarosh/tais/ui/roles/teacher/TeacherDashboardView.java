package ua.org.tees.yarosh.tais.ui.roles.teacher;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import ua.org.tees.yarosh.tais.ui.core.components.Dash;
import ua.org.tees.yarosh.tais.ui.core.components.LayoutPanel;
import ua.org.tees.yarosh.tais.ui.core.components.TeacherPanel;
import ua.org.tees.yarosh.tais.ui.core.components.buttons.CreateTaskButton;


/**
 * @author Timur Yarosh
 *         Date: 21.03.14
 *         Time: 19:57
 */
public class TeacherDashboardView extends VerticalLayout implements View {

    public TeacherDashboardView() {
        setSizeFull();
        addStyleName("dashboard-view");
        HorizontalLayout top = new TeacherPanel("Панель преподавателя");
        addComponent(top);

        Button createTask = new CreateTaskButton();
        top.addComponent(createTask);
        top.setComponentAlignment(createTask, Alignment.MIDDLE_LEFT);

        HorizontalLayout dash = new Dash();
        addComponent(dash);
        setExpandRatio(dash, 1.5f);

        LayoutPanel panelLeft = new LayoutPanel();
        LayoutPanel panelRight = new LayoutPanel();
        dash.addComponent(panelLeft);
        dash.addComponent(panelRight);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}
