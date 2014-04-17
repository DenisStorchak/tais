package ua.org.tees.yarosh.tais.ui.views.common;

import com.vaadin.data.Container;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import org.springframework.beans.factory.annotation.Qualifier;
import ua.org.tees.yarosh.tais.auth.annotations.PermitRoles;
import ua.org.tees.yarosh.tais.ui.TAISUI;
import ua.org.tees.yarosh.tais.ui.components.PlainBorderlessTable;
import ua.org.tees.yarosh.tais.ui.components.layouts.DashboardView;
import ua.org.tees.yarosh.tais.ui.core.mvp.PresentedBy;
import ua.org.tees.yarosh.tais.ui.core.mvp.TaisView;
import ua.org.tees.yarosh.tais.ui.views.common.api.ProfileTais;

import static ua.org.tees.yarosh.tais.core.common.dto.Roles.*;
import static ua.org.tees.yarosh.tais.ui.core.UIFactoryAccessor.getCurrent;
import static ua.org.tees.yarosh.tais.ui.core.api.DataBinds.UriFragments.ME;
import static ua.org.tees.yarosh.tais.ui.views.common.api.ProfileTais.ProfilePresenter;

@PresentedBy(ProfilePresenter.class)
@TaisView("Мой профиль")
@Qualifier(ME)
@PermitRoles({ADMIN, TEACHER, STUDENT})
public class Profile extends DashboardView implements ProfileTais {

    private Table profileTable = new PlainBorderlessTable("Профиль");

    protected Profile() {
        super();
        addDashPanel(null, null, profileTable);
    }

    @Override
    public void update() {
        Container profile = getCurrent().getRelativePresenter(this, ProfilePresenter.class).createProfileContainer();
        if (profile != null) {
            profileTable.setContainerDataSource(profile);
        } else {
            Notification.show("В базе отсутствует нужный профиль");
            TAISUI.navigateBack();
        }
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        update();
    }
}
