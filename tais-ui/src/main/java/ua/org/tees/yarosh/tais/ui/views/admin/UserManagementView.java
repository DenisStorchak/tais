package ua.org.tees.yarosh.tais.ui.views.admin;

import com.vaadin.data.Container;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Table;
import org.springframework.beans.factory.annotation.Qualifier;
import ua.org.tees.yarosh.tais.auth.annotations.PermitRoles;
import ua.org.tees.yarosh.tais.ui.components.PlainBorderlessTable;
import ua.org.tees.yarosh.tais.ui.core.SessionFactory;
import ua.org.tees.yarosh.tais.ui.core.mvp.DashboardLayout;
import ua.org.tees.yarosh.tais.ui.core.mvp.PresentedBy;
import ua.org.tees.yarosh.tais.ui.core.mvp.SpringManagedView;
import ua.org.tees.yarosh.tais.ui.views.admin.api.UserManagementTaisView;

import static ua.org.tees.yarosh.tais.core.common.dto.Roles.ADMIN;
import static ua.org.tees.yarosh.tais.ui.core.DataBinds.UriFragments.Admin.USER_MANAGEMENT;
import static ua.org.tees.yarosh.tais.ui.views.admin.api.UserManagementTaisView.UserManagementPresenter;


@PresentedBy(UserManagementPresenter.class)
@SpringManagedView
@Qualifier(USER_MANAGEMENT)
@PermitRoles(ADMIN)
public class UserManagementView extends DashboardLayout
        implements UserManagementTaisView {

    private Table registrants;

    public UserManagementView() {
        super("Настройка профилей");

        registrants = new PlainBorderlessTable("Все пользователи");
        addDashPanel(null, null, registrants);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        setRegistrantsDataSource(SessionFactory.getCurrent()
                .getRelativePresenter(this, UserManagementPresenter.class).getAllRegistrants());
    }

    @Override
    public void setRegistrantsDataSource(Container registrantsDataSource) {
        registrants.setContainerDataSource(registrantsDataSource);
    }
}
