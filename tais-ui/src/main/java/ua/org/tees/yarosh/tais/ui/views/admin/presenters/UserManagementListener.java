package ua.org.tees.yarosh.tais.ui.views.admin.presenters;

import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.ui.Button;
import com.vaadin.ui.UI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import ua.org.tees.yarosh.tais.core.user.mgmt.api.service.RegistrantService;
import ua.org.tees.yarosh.tais.ui.RoleTranslator;
import ua.org.tees.yarosh.tais.ui.TAISUI;
import ua.org.tees.yarosh.tais.ui.components.windows.CreateClassroomWindow;
import ua.org.tees.yarosh.tais.ui.components.windows.CreateDisciplineWindow;
import ua.org.tees.yarosh.tais.ui.components.windows.CreateGroupWindow;
import ua.org.tees.yarosh.tais.ui.components.windows.CreateRegistrationWindow;
import ua.org.tees.yarosh.tais.ui.core.UIFactoryAccessor;
import ua.org.tees.yarosh.tais.ui.core.api.Updateable;
import ua.org.tees.yarosh.tais.ui.core.mvp.AbstractPresenter;
import ua.org.tees.yarosh.tais.ui.core.mvp.TaisPresenter;
import ua.org.tees.yarosh.tais.ui.views.admin.api.UserManagementTaisView;
import ua.org.tees.yarosh.tais.ui.views.common.api.EditProfileTais;

import static ua.org.tees.yarosh.tais.ui.core.ViewResolver.resolveView;
import static ua.org.tees.yarosh.tais.ui.core.api.DataBinds.UriFragments.Admin.USER_MANAGEMENT;
import static ua.org.tees.yarosh.tais.ui.core.api.DataBinds.UriFragments.EDIT_PROFILE;

@TaisPresenter
@SuppressWarnings("unchecked")
public class UserManagementListener extends AbstractPresenter implements UserManagementTaisView.UserManagementPresenter {

    private static final String KEY_LOGIN = "Логин";
    private static final String KEY_SURNAME = "Фамилия";
    private static final String KEY_NAME = "Имя";
    private static final String KEY_PATRONYMIC = "Отчество";
    private static final String KEY_GROUP = "Группа";
    private static final String KEY_INTERACT_BUTTON = "";
    private static final String KEY_ROLE = "Роль";
    private static final String KEY_EMAIL = "Email";
    private RegistrantService registrantService;

    @Autowired
    public void setRegistrantService(RegistrantService registrantService) {
        this.registrantService = registrantService;
    }

    @Autowired
    public UserManagementListener(@Qualifier(USER_MANAGEMENT) Updateable view) {
        super(view);
    }

    @Override
    public Container getAllRegistrants() {
        IndexedContainer registrantsContainer = new IndexedContainer();
        registrantsContainer.addContainerProperty(KEY_LOGIN, String.class, null);
        registrantsContainer.addContainerProperty(KEY_SURNAME, String.class, null);
        registrantsContainer.addContainerProperty(KEY_NAME, String.class, null);
        registrantsContainer.addContainerProperty(KEY_PATRONYMIC, String.class, null);
        registrantsContainer.addContainerProperty(KEY_EMAIL, String.class, null);
        registrantsContainer.addContainerProperty(KEY_GROUP, String.class, null);
        registrantsContainer.addContainerProperty(KEY_ROLE, String.class, null);
        registrantsContainer.addContainerProperty(KEY_INTERACT_BUTTON, Button.class, null);

        registrantService.findAllRegistrants().forEach(r -> {
            Item item = registrantsContainer.addItem(r.getLogin());
            item.getItemProperty(KEY_LOGIN).setValue(r.getLogin());
            item.getItemProperty(KEY_SURNAME).setValue(r.getSurname());
            item.getItemProperty(KEY_NAME).setValue(r.getName());
            item.getItemProperty(KEY_PATRONYMIC).setValue(r.getPatronymic());
            item.getItemProperty(KEY_EMAIL).setValue(r.getEmail());
            item.getItemProperty(KEY_GROUP).setValue(r.getGroup() != null ? String.valueOf(r.getGroup().getId()) : null);
            item.getItemProperty(KEY_ROLE).setValue(RoleTranslator.translate(r.getRole()));
            item.getItemProperty(KEY_INTERACT_BUTTON).setValue(new Button("Редактировать", createListener(r.getLogin())));
        });
        return registrantsContainer;
    }

    @Override
    public void onCreateRegistration() {
//        UI.getCurrent().getNavigator().navigateTo(USER_REGISTRATION);
        UI.getCurrent().addWindow(UIFactoryAccessor.getCurrent().getWindow(CreateRegistrationWindow.class));
    }

    @Override
    public void onCreateGroup() {
        UI.getCurrent().addWindow(UIFactoryAccessor.getCurrent().getWindow(CreateGroupWindow.class));
    }

    @Override
    public void onCreateClassroom() {
        UI.getCurrent().addWindow(UIFactoryAccessor.getCurrent().getWindow(CreateClassroomWindow.class));
    }

    @Override
    public void onCreateDiscipline() {
        UI.getCurrent().addWindow(UIFactoryAccessor.getCurrent().getWindow(CreateDisciplineWindow.class));
    }

    private Button.ClickListener createListener(String login) {
        return event -> {
            TAISUI.navigateTo(EDIT_PROFILE);
            EditProfileTais.EditProfilePresenter presenter = UIFactoryAccessor.getCurrent()
                    .getRelativePresenter(resolveView(EDIT_PROFILE), EditProfileTais.EditProfilePresenter.class);
            presenter.setRegistrantId(login);
            presenter.allowAdminRights(true);
            presenter.update();
        };
    }
}
