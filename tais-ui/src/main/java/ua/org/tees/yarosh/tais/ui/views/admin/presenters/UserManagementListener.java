package ua.org.tees.yarosh.tais.ui.views.admin.presenters;

import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.ui.Button;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import ua.org.tees.yarosh.tais.core.user.mgmt.api.service.RegistrantService;
import ua.org.tees.yarosh.tais.ui.RoleTranslator;
import ua.org.tees.yarosh.tais.ui.core.mvp.AbstractPresenter;
import ua.org.tees.yarosh.tais.ui.core.mvp.UpdatableView;
import ua.org.tees.yarosh.tais.ui.views.admin.api.UserManagementTaisView;

import static ua.org.tees.yarosh.tais.ui.core.DataBinds.UriFragments.Admin.USER_MANAGEMENT;

@Service
@Scope("prototype")
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
    public UserManagementListener(@Qualifier(USER_MANAGEMENT) UpdatableView view) {
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
            item.getItemProperty(KEY_GROUP).setValue(String.valueOf(r.getGroup().getId()));
            item.getItemProperty(KEY_ROLE).setValue(RoleTranslator.translate(r.getRole()));
            item.getItemProperty(KEY_INTERACT_BUTTON).setValue(new Button("Редактировать"));
        });
        return registrantsContainer;
    }
}
