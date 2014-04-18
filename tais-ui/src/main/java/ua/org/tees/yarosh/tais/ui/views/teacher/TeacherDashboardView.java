package ua.org.tees.yarosh.tais.ui.views.teacher;

import com.vaadin.data.Item;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
import org.springframework.beans.factory.annotation.Qualifier;
import ua.org.tees.yarosh.tais.auth.api.annotations.PermitRoles;
import ua.org.tees.yarosh.tais.homework.models.ManualTaskReport;
import ua.org.tees.yarosh.tais.ui.components.PlainBorderlessTable;
import ua.org.tees.yarosh.tais.ui.components.layouts.DashboardView;
import ua.org.tees.yarosh.tais.ui.core.mvp.PresentedBy;
import ua.org.tees.yarosh.tais.ui.core.mvp.TaisView;
import ua.org.tees.yarosh.tais.ui.views.teacher.api.TeacherDashboardTaisView;

import java.util.Date;
import java.util.List;

import static ua.org.tees.yarosh.tais.core.common.dto.Roles.TEACHER;
import static ua.org.tees.yarosh.tais.ui.core.UIFactory.getCurrent;
import static ua.org.tees.yarosh.tais.ui.core.VaadinUtils.transformToIconOnlyButton;
import static ua.org.tees.yarosh.tais.ui.core.api.DataBinds.UriFragments.Teacher.TEACHER_DASHBOARD;
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

    private static final String PROPERTY_DISCIPLINE = "Дисциплина";
    private static final String PROPERTY_GROUP = "Группа";
    private static final String PROPERTY_DEADLINE = "Дедлайн";
    private static final String PROPERTY_OWNER = "Автор";
    private static final String PROPERTY_INTERACTION = "Взаимодействие";
    private Table unratedReports = new PlainBorderlessTable("Непроверенные отчеты");
    private Button createManualTask = new Button();
    private Button createQuestionsSuite = new Button();
    private Button refreshReportsTable = new Button();

    public TeacherDashboardView() {
        super();
        top.addComponents(createManualTask, createQuestionsSuite, refreshReportsTable);

        addDashPanel(null, null, unratedReports);
    }

    @Override
    public void init() {
        super.init();
        TeacherDashboardPresenter p = getCurrent().getRelativePresenter(this, TeacherDashboardPresenter.class);
        transformToIconOnlyButton("Добавить задание", "icon-doc-new", e -> p.onCreateManualTask(), createManualTask);
        transformToIconOnlyButton("Создать тесты", "icon-doc-new", e -> p.onCreateQuestionsSuite(), createQuestionsSuite);
        transformToIconOnlyButton("Обновить список отчетов", "icon-doc-new", e -> p.onUpdate(), refreshReportsTable);

        unratedReports.addContainerProperty(PROPERTY_DISCIPLINE, String.class, null);
        unratedReports.addContainerProperty(PROPERTY_DEADLINE, Date.class, null);
        unratedReports.addContainerProperty(PROPERTY_GROUP, String.class, null);
        unratedReports.addContainerProperty(PROPERTY_OWNER, String.class, null);
        unratedReports.addContainerProperty(PROPERTY_INTERACTION, HorizontalLayout.class, null);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        update();
    }

    @Override
    public void setUnrated(List<ManualTaskReport> unratedReports) {
        TeacherDashboardPresenter p = getCurrent().getRelativePresenter(this, TeacherDashboardPresenter.class);
        this.unratedReports.removeAllItems();
        for (ManualTaskReport unratedReport : unratedReports) {
            HorizontalLayout controls = new HorizontalLayout();
            Button details = new Button();
            transformToIconOnlyButton("Подробнее", "icon-doc-new", e -> p.onDetails(unratedReport), details);
            controls.addComponent(details);

            Item item = this.unratedReports.addItem(unratedReport.getId());
            item.getItemProperty(PROPERTY_DISCIPLINE).setValue(unratedReport.getTask().getDiscipline().toString());
            item.getItemProperty(PROPERTY_DEADLINE).setValue(unratedReport.getTask().getDeadline());
            item.getItemProperty(PROPERTY_GROUP).setValue(unratedReport.getTask().getStudentGroup().toString());
            item.getItemProperty(PROPERTY_OWNER).setValue(unratedReport.getOwner().toString());
            item.getItemProperty(PROPERTY_INTERACTION).setValue(controls);
        }
    }
}
