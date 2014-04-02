package ua.org.tees.yarosh.tais.ui.views.teacher;

import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import ua.org.tees.yarosh.tais.auth.annotations.PermitRoles;
import ua.org.tees.yarosh.tais.ui.components.BgPanel;
import ua.org.tees.yarosh.tais.ui.components.DashPanel;
import ua.org.tees.yarosh.tais.ui.components.HorizontalDash;
import ua.org.tees.yarosh.tais.ui.components.PlainBorderlessTable;
import ua.org.tees.yarosh.tais.ui.components.buttons.CreateTaskButton;
import ua.org.tees.yarosh.tais.ui.core.SessionFactory;
import ua.org.tees.yarosh.tais.ui.core.mvp.AbstractTaisLayout;
import ua.org.tees.yarosh.tais.ui.core.mvp.PresentedBy;
import ua.org.tees.yarosh.tais.ui.views.teacher.api.TeacherDashboardTaisView;

import static ua.org.tees.yarosh.tais.core.common.dto.Role.TEACHER;
import static ua.org.tees.yarosh.tais.ui.core.text.UriFragments.Teacher.TEACHER_DASHBOARD;
import static ua.org.tees.yarosh.tais.ui.views.teacher.api.TeacherDashboardTaisView.TeacherDashboardPresenter;


/**
 * @author Timur Yarosh
 *         Date: 21.03.14
 *         Time: 19:57
 */
@PresentedBy(TeacherDashboardPresenter.class)
@Service
@Qualifier(TEACHER_DASHBOARD)
@Scope("prototype")
@PermitRoles(TEACHER)
public class TeacherDashboardView extends AbstractTaisLayout implements TeacherDashboardTaisView {

    private Table unratedReports;

    public TeacherDashboardView() {
        setSizeFull();
        addStyleName("dashboard-view");
        HorizontalLayout top = new BgPanel("Задания");
        addComponent(top);

        Button createTask = new CreateTaskButton();
        top.addComponent(createTask);
        top.setComponentAlignment(createTask, Alignment.MIDDLE_LEFT);

        HorizontalLayout dash = new HorizontalDash();
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
        unratedReports.setContainerDataSource(SessionFactory.getCurrent().getPresenter(TeacherDashboardPresenter.class)
                .getUnratedManualReports());
    }
}
