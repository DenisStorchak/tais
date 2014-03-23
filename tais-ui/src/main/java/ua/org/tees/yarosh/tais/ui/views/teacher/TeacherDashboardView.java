package ua.org.tees.yarosh.tais.ui.views.teacher;

import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import ua.org.tees.yarosh.tais.ui.core.components.BgPanel;
import ua.org.tees.yarosh.tais.ui.core.components.Dash;
import ua.org.tees.yarosh.tais.ui.core.components.DashPanel;
import ua.org.tees.yarosh.tais.ui.core.components.PlainBorderlessTable;
import ua.org.tees.yarosh.tais.ui.core.components.buttons.CreateTaskButton;
import ua.org.tees.yarosh.tais.ui.core.mvp.AbstractPresenter;
import ua.org.tees.yarosh.tais.ui.core.mvp.PresenterClass;

import java.util.LinkedList;

import static ua.org.tees.yarosh.tais.ui.core.UriFragments.Teacher.TEACHER_DASHBOARD;


/**
 * @author Timur Yarosh
 *         Date: 21.03.14
 *         Time: 19:57
 */
@PresenterClass(TeacherDashboardListener.class)
@Service
@Qualifier(TEACHER_DASHBOARD)
@Scope("prototype")
public class TeacherDashboardView extends VerticalLayout implements TeacherDashboardTaisView {

    private LinkedList<TeacherDashboardPresenter> presenters = new LinkedList<>();
    private Table unratedReports;

    public TeacherDashboardView() {
        setSizeFull();
        addStyleName("dashboard-view");
        HorizontalLayout top = new BgPanel("Задания");
        addComponent(top);

        Button createTask = new CreateTaskButton();
        top.addComponent(createTask);
        top.setComponentAlignment(createTask, Alignment.MIDDLE_LEFT);

        HorizontalLayout dash = new Dash();
        addComponent(dash);
        setExpandRatio(dash, 1.5f);

        DashPanel panelLeft = new DashPanel();

        unratedReports = new PlainBorderlessTable("Непроверенные отчеты");
        panelLeft.addComponent(unratedReports);
        DashPanel panelRight = new DashPanel();
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
