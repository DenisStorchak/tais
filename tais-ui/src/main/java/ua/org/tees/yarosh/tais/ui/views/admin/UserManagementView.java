package ua.org.tees.yarosh.tais.ui.views.admin;

import com.vaadin.data.Container;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Table;
import org.springframework.beans.factory.annotation.Qualifier;
import ua.org.tees.yarosh.tais.auth.annotations.PermitRoles;
import ua.org.tees.yarosh.tais.ui.components.PlainBorderlessTable;
import ua.org.tees.yarosh.tais.ui.components.layouts.DashboardView;
import ua.org.tees.yarosh.tais.ui.core.mvp.PresentedBy;
import ua.org.tees.yarosh.tais.ui.core.mvp.TaisView;
import ua.org.tees.yarosh.tais.ui.views.admin.api.UserManagementTaisView;

import static ua.org.tees.yarosh.tais.core.common.dto.Roles.ADMIN;
import static ua.org.tees.yarosh.tais.ui.core.DataBinds.UriFragments.Admin.USER_MANAGEMENT;
import static ua.org.tees.yarosh.tais.ui.core.SessionFactory.getCurrent;
import static ua.org.tees.yarosh.tais.ui.core.VaadinUtils.transformToIconOnlyButton;
import static ua.org.tees.yarosh.tais.ui.views.admin.api.UserManagementTaisView.UserManagementPresenter;


@PresentedBy(UserManagementPresenter.class)
@TaisView("Все профили")
@Qualifier(USER_MANAGEMENT)
@PermitRoles(ADMIN)
public class UserManagementView extends DashboardView
        implements UserManagementTaisView {

    private Table registrants = new PlainBorderlessTable("Все пользователи");
    private Button createRegistration = new Button();
    private Button createGroup = new Button();
    private Button createClassroom = new Button();
    private Button createDiscipline = new Button();

    @Override
    public void init() {
        super.init();
        UserManagementPresenter p = getCurrent().getRelativePresenter(this, UserManagementPresenter.class);
        transformToIconOnlyButton("Регистрация", "icon-user-add", e -> p.onCreateRegistration(), createRegistration);
        transformToIconOnlyButton("Создать новую группу", "icon-doc-new", e -> p.onCreateGroup(), createGroup);
        transformToIconOnlyButton("Создать новую аудиторию", "icon-doc-new", e -> p.onCreateClassroom(), createClassroom);
        transformToIconOnlyButton("Создать новую дисциплину", "icon-doc-new", e -> p.onCreateDiscipline(), createDiscipline);
    }

    public UserManagementView() {
        super();
        addDashPanel(null, null, registrants);
        top.addComponents(createRegistration, createGroup, createClassroom, createDiscipline);
    }

    @Override
    public void update() {
        setRegistrantsDataSource(getCurrent().getRelativePresenter(this, UserManagementPresenter.class)
                .getAllRegistrants());
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        update();
    }

    @Override
    public void setRegistrantsDataSource(Container registrantsDataSource) {
        registrants.setContainerDataSource(registrantsDataSource);
    }
}
