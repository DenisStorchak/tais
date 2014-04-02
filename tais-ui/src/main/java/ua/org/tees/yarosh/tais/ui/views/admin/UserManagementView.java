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
import ua.org.tees.yarosh.tais.ui.components.DashPanel;
import ua.org.tees.yarosh.tais.ui.components.HorizontalDash;
import ua.org.tees.yarosh.tais.ui.components.PlainBorderlessTable;
import ua.org.tees.yarosh.tais.ui.core.SessionFactory;
import ua.org.tees.yarosh.tais.ui.core.mvp.AbstractTaisLayout;
import ua.org.tees.yarosh.tais.ui.core.mvp.PresentedBy;
import ua.org.tees.yarosh.tais.ui.views.admin.api.UserManagementTaisView;

import static ua.org.tees.yarosh.tais.core.common.dto.Role.ADMIN;
import static ua.org.tees.yarosh.tais.ui.core.text.UriFragments.Admin.USER_MANAGEMENT;
import static ua.org.tees.yarosh.tais.ui.views.admin.api.UserManagementTaisView.UserManagementPresenter;


@PresentedBy(UserManagementPresenter.class)
@Service
@Qualifier(USER_MANAGEMENT)
@Scope("prototype")
@PermitRoles(ADMIN)
public class UserManagementView extends AbstractTaisLayout
        implements UserManagementTaisView {

    private Table registrants;

    public UserManagementView() {
        setSizeFull();
        addStyleName("dashboard-view");
        HorizontalLayout top = new BgPanel("Настройка профилей");
        addComponent(top);

        HorizontalLayout dash = new HorizontalDash();
        addComponent(dash);
        setExpandRatio(dash, 1.5f);

        DashPanel dashPanel = new DashPanel();
        registrants = new PlainBorderlessTable("Все пользователи");
        dashPanel.addComponent(registrants);
        dash.addComponent(dashPanel);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        setRegistrantsDataSource(SessionFactory.getCurrent().getPresenter(UserManagementPresenter.class).getAllRegistrants());
    }

    @Override
    public void setRegistrantsDataSource(Container registrantsDataSource) {
        registrants.setContainerDataSource(registrantsDataSource);
    }
}
