package ua.org.tees.yarosh.tais.ui.views.common;

import com.vaadin.data.Container;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import org.springframework.beans.factory.annotation.Qualifier;
import ua.org.tees.yarosh.tais.auth.annotations.PermitRoles;
import ua.org.tees.yarosh.tais.ui.components.PlainBorderlessTable;
import ua.org.tees.yarosh.tais.ui.core.ViewResolver;
import ua.org.tees.yarosh.tais.ui.core.mvp.DashboardLayout;
import ua.org.tees.yarosh.tais.ui.core.mvp.PresentedBy;
import ua.org.tees.yarosh.tais.ui.core.mvp.SpringManagedView;
import ua.org.tees.yarosh.tais.ui.views.common.api.ProfileTaisView;

import static ua.org.tees.yarosh.tais.core.common.dto.Roles.*;
import static ua.org.tees.yarosh.tais.ui.core.DataBinds.SessionKeys.LAST_VIEW;
import static ua.org.tees.yarosh.tais.ui.core.DataBinds.UriFragments.ME;
import static ua.org.tees.yarosh.tais.ui.core.SessionFactory.getCurrent;
import static ua.org.tees.yarosh.tais.ui.views.common.api.ProfileTaisView.ProfilePresenter;

@PresentedBy(ProfilePresenter.class)
@SpringManagedView
@Qualifier(ME)
@PermitRoles({ADMIN, TEACHER, STUDENT})
public class ProfileView extends DashboardLayout implements ProfileTaisView {

    private Table profileTable = new PlainBorderlessTable("Профиль");

    protected ProfileView() {
        super("Мой профиль");
        addDashPanel(null, null, profileTable);
    }

    @Override
    public void update() {
        Container profile = getCurrent().getRelativePresenter(this, ProfilePresenter.class).createProfileContainer();
        if (profile != null) {
            profileTable.setContainerDataSource(profile);
        } else {
            Notification.show("В базе отсутствует нужный профиль");
            View previousView = (View) VaadinSession.getCurrent().getAttribute(LAST_VIEW);
            getUI().getNavigator().navigateTo(ViewResolver.extractName(previousView));
        }
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        update();
    }
}
