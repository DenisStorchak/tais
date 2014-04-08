package ua.org.tees.yarosh.tais.ui.views.teacher;

import com.vaadin.data.Container;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Table;
import ua.org.tees.yarosh.tais.auth.annotations.PermitRoles;
import ua.org.tees.yarosh.tais.ui.components.PlainBorderlessTable;
import ua.org.tees.yarosh.tais.ui.components.layouts.DashPanel;
import ua.org.tees.yarosh.tais.ui.components.layouts.DashboardView;
import ua.org.tees.yarosh.tais.ui.core.SessionFactory;
import ua.org.tees.yarosh.tais.ui.core.mvp.PresentedBy;
import ua.org.tees.yarosh.tais.ui.core.mvp.TaisView;
import ua.org.tees.yarosh.tais.ui.views.teacher.api.ListEnabledQuestionsSuiteTaisView;

import static ua.org.tees.yarosh.tais.core.common.dto.Roles.TEACHER;
import static ua.org.tees.yarosh.tais.ui.views.teacher.api.ListEnabledQuestionsSuiteTaisView.ListEnabledQuestionsSuitePresenter;

@PresentedBy(ListEnabledQuestionsSuitePresenter.class)
@PermitRoles(TEACHER)
@TaisView("Все тесты")
public class ListEnabledQuestionsSuiteView extends DashboardView implements ListEnabledQuestionsSuiteTaisView {

    private Table contentTable = new PlainBorderlessTable("");

    public ListEnabledQuestionsSuiteView() {
        DashPanel dashPanel = addDashPanel(null, null, contentTable);
        dashPanel.setSizeUndefined();
        dashPanel.setWidth(50, Unit.PERCENTAGE);
    }

    @Override
    public void update() {
        Container suites = SessionFactory.getCurrent().getRelativePresenter(this, ListEnabledQuestionsSuitePresenter.class)
                .suitesContainer();
        contentTable.setContainerDataSource(suites);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        update();
    }
}
