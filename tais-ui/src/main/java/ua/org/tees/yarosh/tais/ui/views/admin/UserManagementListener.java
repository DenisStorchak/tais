package ua.org.tees.yarosh.tais.ui.views.admin;

import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.ui.Button;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import ua.org.tees.yarosh.tais.core.user.mgmt.api.service.RegistrantService;
import ua.org.tees.yarosh.tais.ui.core.HelpManager;
import ua.org.tees.yarosh.tais.ui.core.components.PresenterBasedView;
import ua.org.tees.yarosh.tais.ui.core.mvp.AbstractPresenter;

import static ua.org.tees.yarosh.tais.ui.core.UriFragments.Admin.USER_MANAGEMENT;

@Service
@Scope("prototype")
public class UserManagementListener extends AbstractPresenter implements UserListTaisView.UserListPresenter {

    private static final String KEY_LOGIN = "Логин";
    private static final String KEY_SURNAME = "Фамилия";
    private static final String KEY_NAME = "Имя";
    private static final String KEY_PATRONYMIC = "Отчество";
    private static final String KEY_GROUP = "Группа";
    private static final String KEY_INTERACT_BUTTON = "";
    private RegistrantService registrantService;

    @Autowired
    public void setRegistrantService(RegistrantService registrantService) {
        this.registrantService = registrantService;
    }

    @Autowired
    public UserManagementListener(@Qualifier(USER_MANAGEMENT) PresenterBasedView view, HelpManager helpManager) {
        super(view, helpManager);
    }

    @Override
    public Container getAllRegistrants() {
        IndexedContainer registrantsContainer = new IndexedContainer();
        registrantsContainer.addContainerProperty(KEY_LOGIN, String.class, null);
        registrantsContainer.addContainerProperty(KEY_SURNAME, String.class, null);
        registrantsContainer.addContainerProperty(KEY_NAME, String.class, null);
        registrantsContainer.addContainerProperty(KEY_PATRONYMIC, String.class, null);
        registrantsContainer.addContainerProperty(KEY_GROUP, String.class, null);
        registrantsContainer.addContainerProperty(KEY_INTERACT_BUTTON, Button.class, null);

        registrantService.listAllRegistrants().forEach(r -> {
            Item item = registrantsContainer.addItem(r.getLogin());
            item.getItemProperty(KEY_LOGIN).setValue(r.getLogin());
            item.getItemProperty(KEY_SURNAME).setValue(r.getSurname());
            item.getItemProperty(KEY_NAME).setValue(r.getName());
            item.getItemProperty(KEY_PATRONYMIC).setValue(r.getPatronymic());
            item.getItemProperty(KEY_GROUP).setValue(String.valueOf(r.getGroup().getId()));
            item.getItemProperty(KEY_INTERACT_BUTTON).setValue(new Button("Редактировать"));
        });
        return registrantsContainer;
    }
}
