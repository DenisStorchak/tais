package ua.org.tees.yarosh.tais.ui.views.admin;

import com.vaadin.data.Container;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import ua.org.tees.yarosh.tais.ui.core.components.BgPanel;
import ua.org.tees.yarosh.tais.ui.core.components.Dash;
import ua.org.tees.yarosh.tais.ui.core.components.DashPanel;
import ua.org.tees.yarosh.tais.ui.core.components.PlainBorderlessTable;
import ua.org.tees.yarosh.tais.ui.core.mvp.PresenterBasedVerticalLayoutView;
import ua.org.tees.yarosh.tais.ui.core.mvp.PresenterClass;

import static ua.org.tees.yarosh.tais.ui.core.UriFragments.Admin.USER_MANAGEMENT;
import static ua.org.tees.yarosh.tais.ui.views.admin.UserManagementTaisView.UserManagementPresenter;


@PresenterClass(UserManagementListener.class)
@Service
@Qualifier(USER_MANAGEMENT)
@Scope("prototype")
public class UserManagementView extends PresenterBasedVerticalLayoutView<UserManagementPresenter>
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
        setRegistrantsDataSource(primaryPresenter().getAllRegistrants());
    }

    @Override
    public void setRegistrantsDataSource(Container registrantsDataSource) {
        registrants.setContainerDataSource(registrantsDataSource);
    }
}
