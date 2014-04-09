package ua.org.tees.yarosh.tais.ui.views.teacher;

import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Table;
import org.springframework.beans.factory.annotation.Qualifier;
import ua.org.tees.yarosh.tais.auth.annotations.PermitRoles;
import ua.org.tees.yarosh.tais.ui.components.PlainBorderlessTable;
import ua.org.tees.yarosh.tais.ui.components.buttons.CreateTaskButton;
import ua.org.tees.yarosh.tais.ui.components.layouts.DashboardView;
import ua.org.tees.yarosh.tais.ui.core.SessionFactory;
import ua.org.tees.yarosh.tais.ui.core.mvp.PresentedBy;
import ua.org.tees.yarosh.tais.ui.core.mvp.TaisView;
import ua.org.tees.yarosh.tais.ui.views.teacher.api.TeacherDashboardTais;

import static ua.org.tees.yarosh.tais.core.common.dto.Roles.TEACHER;
import static ua.org.tees.yarosh.tais.ui.core.DataBinds.UriFragments.Teacher.TEACHER_DASHBOARD;
import static ua.org.tees.yarosh.tais.ui.views.teacher.api.TeacherDashboardTais.TeacherDashboardPresenter;


/**
 * @author Timur Yarosh
 *         Date: 21.03.14
 *         Time: 19:57
 */
@PresentedBy(TeacherDashboardPresenter.class)
@TaisView("Задания")
@Qualifier(TEACHER_DASHBOARD)
@PermitRoles(TEACHER)
public class TeacherDashboard extends DashboardView implements TeacherDashboardTais {

    private Table unratedReports;

    public TeacherDashboard() {
        super();

        Button createTask = new CreateTaskButton();
        top.addComponent(createTask);
        top.setComponentAlignment(createTask, Alignment.MIDDLE_LEFT);

        unratedReports = new PlainBorderlessTable("Непроверенные отчеты");
        addDashPanel(null, null, unratedReports);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        unratedReports.setContainerDataSource(SessionFactory.getCurrent()
                .getRelativePresenter(this, TeacherDashboardPresenter.class)
                .getUnratedManualReports());
    }
}
