package ua.org.tees.yarosh.tais.ui.views.teacher;

import com.vaadin.data.Container;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Table;
import org.springframework.beans.factory.annotation.Qualifier;
import ua.org.tees.yarosh.tais.auth.annotations.PermitRoles;
import ua.org.tees.yarosh.tais.ui.components.PlainBorderlessTable;
import ua.org.tees.yarosh.tais.ui.components.layouts.DashPanel;
import ua.org.tees.yarosh.tais.ui.components.layouts.DashboardView;
import ua.org.tees.yarosh.tais.ui.core.SessionFactory;
import ua.org.tees.yarosh.tais.ui.core.mvp.PresentedBy;
import ua.org.tees.yarosh.tais.ui.core.mvp.TaisView;
import ua.org.tees.yarosh.tais.ui.views.teacher.api.EnabledQuestionsSuiteTaisView;

import static ua.org.tees.yarosh.tais.core.common.dto.Roles.TEACHER;
import static ua.org.tees.yarosh.tais.ui.core.DataBinds.UriFragments.Teacher.ENABLED_QUESTIONS;
import static ua.org.tees.yarosh.tais.ui.views.teacher.api.EnabledQuestionsSuiteTaisView.EnabledQuestionsSuitePresenter;

@PresentedBy(EnabledQuestionsSuitePresenter.class)
@PermitRoles(TEACHER)
@Qualifier(ENABLED_QUESTIONS)
@TaisView("Все тесты")
public class EnabledQuestionsSuiteView extends DashboardView implements EnabledQuestionsSuiteTaisView {

    private Table contentTable = new PlainBorderlessTable("");

    public EnabledQuestionsSuiteView() {
        DashPanel dashPanel = addDashPanel(null, null, contentTable);
        dashPanel.setSizeUndefined();
        dashPanel.setWidth(50, Unit.PERCENTAGE);
    }

    @Override
    public void update() {
        Container suites = SessionFactory.getCurrent().getRelativePresenter(this, EnabledQuestionsSuitePresenter.class)
                .suitesContainer();
        contentTable.setContainerDataSource(suites);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        update();
    }
}
