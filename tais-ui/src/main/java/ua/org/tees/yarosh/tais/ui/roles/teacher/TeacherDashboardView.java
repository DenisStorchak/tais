package ua.org.tees.yarosh.tais.ui.roles.teacher;

import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import ua.org.tees.yarosh.tais.ui.core.components.Dash;
import ua.org.tees.yarosh.tais.ui.core.components.LayoutPanel;
import ua.org.tees.yarosh.tais.ui.core.components.TeacherPanel;
import ua.org.tees.yarosh.tais.ui.core.components.UnratedReportsTable;
import ua.org.tees.yarosh.tais.ui.core.components.buttons.CreateTaskButton;
import ua.org.tees.yarosh.tais.ui.core.mvp.AbstractPresenter;
import ua.org.tees.yarosh.tais.ui.core.mvp.PresenterClass;

import java.util.LinkedList;


/**
 * @author Timur Yarosh
 *         Date: 21.03.14
 *         Time: 19:57
 */
@PresenterClass(TeacherDashboardListener.class)
public class TeacherDashboardView extends VerticalLayout implements TeacherDashboardTaisView {

    private LinkedList<TeacherDashboardPresenter> presenters = new LinkedList<>();
    private Table unratedReports;

    public TeacherDashboardView() {
        setSizeFull();
        addStyleName("dashboard-view");
        HorizontalLayout top = new TeacherPanel("Задания");
        addComponent(top);

        Button createTask = new CreateTaskButton();
        top.addComponent(createTask);
        top.setComponentAlignment(createTask, Alignment.MIDDLE_LEFT);

        HorizontalLayout dash = new Dash();
        addComponent(dash);
        setExpandRatio(dash, 1.5f);

        LayoutPanel panelLeft = new LayoutPanel();

        unratedReports = new UnratedReportsTable();
        panelLeft.addComponent(unratedReports);
        LayoutPanel panelRight = new LayoutPanel();
        dash.addComponent(panelLeft);
        dash.addComponent(panelRight);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        unratedReports.setContainerDataSource(presenters.getFirst().getUnratedManualReports());
    }

    @Override
    public void addPresenter(AbstractPresenter presenter) {
        presenters.add((TeacherDashboardPresenter) presenter);
    }
}
