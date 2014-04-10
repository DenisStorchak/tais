package ua.org.tees.yarosh.tais.ui.views.teacher;

import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Table;
import org.springframework.beans.factory.annotation.Qualifier;
import ua.org.tees.yarosh.tais.auth.annotations.PermitRoles;
import ua.org.tees.yarosh.tais.ui.components.PlainBorderlessTable;
import ua.org.tees.yarosh.tais.ui.components.layouts.DashboardView;
import ua.org.tees.yarosh.tais.ui.core.mvp.PresentedBy;
import ua.org.tees.yarosh.tais.ui.core.mvp.TaisView;
import ua.org.tees.yarosh.tais.ui.views.teacher.api.TeacherDashboardTaisView;

import static ua.org.tees.yarosh.tais.core.common.dto.Roles.TEACHER;
import static ua.org.tees.yarosh.tais.ui.core.DataBinds.UriFragments.Teacher.TEACHER_DASHBOARD;
import static ua.org.tees.yarosh.tais.ui.core.SessionFactory.getCurrent;
import static ua.org.tees.yarosh.tais.ui.views.teacher.api.TeacherDashboardTaisView.TeacherDashboardPresenter;


/**
 * @author Timur Yarosh
 *         Date: 21.03.14
 *         Time: 19:57
 */
@PresentedBy(TeacherDashboardPresenter.class)
@TaisView("Отчеты")
@Qualifier(TEACHER_DASHBOARD)
@PermitRoles(TEACHER)
public class TeacherDashboardView extends DashboardView implements TeacherDashboardTaisView {

    private Table unratedReports = new PlainBorderlessTable("Непроверенные отчеты");
    private Button createManualTask = new Button();
    private Button createQuestionsSuite = new Button();

    public TeacherDashboardView() {
        super();
        top.addComponents(createManualTask, createQuestionsSuite);

        addDashPanel(null, null, unratedReports);
    }

    @Override
    public void init() {
        super.init();
        TeacherDashboardPresenter p = getCurrent().getRelativePresenter(this, TeacherDashboardPresenter.class);
        setUpButton("Добавить задание", "icon-doc-new", e -> p.onCreateManualTask(), createManualTask);
        setUpButton("Создать тесты", "icon-doc-new", e -> p.onCreateQuestionsSuite(), createQuestionsSuite);
    }

    private void setUpButton(String description, String icon, Button.ClickListener listener, Button button) {
        button.setDescription(description);
        button.addStyleName(icon);
        button.addStyleName("icon-only");
        button.addClickListener(listener);
    }

    @Override
    public void update() {
        unratedReports.setContainerDataSource(getCurrent()
                .getRelativePresenter(this, TeacherDashboardPresenter.class)
                .getUnratedManualReports());
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        update();
    }
}
