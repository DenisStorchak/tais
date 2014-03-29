package ua.org.tees.yarosh.tais.ui.views.admin;

import com.vaadin.data.Container;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import ua.org.tees.yarosh.tais.auth.annotations.PermitRoles;
import ua.org.tees.yarosh.tais.ui.components.BgPanel;
import ua.org.tees.yarosh.tais.ui.components.Dash;
import ua.org.tees.yarosh.tais.ui.components.DashPanel;
import ua.org.tees.yarosh.tais.ui.components.PlainBorderlessTable;
import ua.org.tees.yarosh.tais.ui.core.mvp.PresentedBy;
import ua.org.tees.yarosh.tais.ui.core.mvp.PresenterBasedVerticalLayoutView;
import ua.org.tees.yarosh.tais.ui.views.admin.api.UserManagementTaisView;
import ua.org.tees.yarosh.tais.ui.views.admin.presenters.UserManagementListener;

import static ua.org.tees.yarosh.tais.core.common.dto.Role.ADMIN;
import static ua.org.tees.yarosh.tais.ui.core.text.UriFragments.Admin.USER_MANAGEMENT;


@PresentedBy(UserManagementListener.class)
@Service
@Qualifier(USER_MANAGEMENT)
@Scope("prototype")
@PermitRoles(ADMIN)
public class UserManagementView extends PresenterBasedVerticalLayoutView<UserManagementListener>
        implements UserManagementTaisView {

    private Table registrants;

    public UserManagementView() {
        setSizeFull();
        addStyleName("dashboard-view");
        HorizontalLayout top = new BgPanel("Настройка профилей");
        addComponent(top);

        HorizontalLayout dash = new Dash();
        addComponent(dash);
        setExpandRatio(dash, 1.5f);

        DashPanel dashPanel = new DashPanel();
        registrants = new PlainBorderlessTable("Все пользователи");
        dashPanel.addComponent(registrants);
        dash.addComponent(dashPanel);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        setRegistrantsDataSource(presenter().getAllRegistrants());
    }

    @Override
    public void setRegistrantsDataSource(Container registrantsDataSource) {
        registrants.setContainerDataSource(registrantsDataSource);
    }
}
