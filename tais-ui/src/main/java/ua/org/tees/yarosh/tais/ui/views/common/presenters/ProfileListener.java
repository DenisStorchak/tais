package ua.org.tees.yarosh.tais.ui.views.common.presenters;

import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.server.VaadinSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import ua.org.tees.yarosh.tais.core.common.models.Registrant;
import ua.org.tees.yarosh.tais.core.user.mgmt.api.service.RegistrantService;
import ua.org.tees.yarosh.tais.ui.core.api.UIContext;
import ua.org.tees.yarosh.tais.ui.core.api.UpdatableView;
import ua.org.tees.yarosh.tais.ui.core.mvp.AbstractPresenter;
import ua.org.tees.yarosh.tais.ui.core.mvp.TaisPresenter;

import static ua.org.tees.yarosh.tais.ui.core.DataBinds.SessionKeys.REGISTRANT_ID;
import static ua.org.tees.yarosh.tais.ui.core.DataBinds.UriFragments.ME;
import static ua.org.tees.yarosh.tais.ui.views.common.api.ProfileTaisView.ProfilePresenter;

@TaisPresenter
@SuppressWarnings("unchecked")
public class ProfileListener extends AbstractPresenter implements ProfilePresenter {

    private static final String KEY_PROPERTY = "";
    private static final String VALUE_PROPERTY = " ";
    private UIContext ctx;

    @Autowired
    public void setCtx(UIContext ctx) {
        this.ctx = ctx;
    }

    @Autowired
    public ProfileListener(@Qualifier(ME) UpdatableView view) {
        super(view);
    }

    @Override
    public Container createProfileContainer() {
        String login = (String) VaadinSession.getCurrent().getAttribute(REGISTRANT_ID);
        Registrant registrant = ctx.getBean(RegistrantService.class).getRegistration(login);

        if (registrant == null) {
            return null;
        }

        IndexedContainer container = new IndexedContainer();
        container.addContainerProperty(KEY_PROPERTY, String.class, null);
        container.addContainerProperty(VALUE_PROPERTY, String.class, null);

        addItem(container, "Логин", registrant.getLogin());
        addItem(container, "Фамилия", registrant.getSurname());
        addItem(container, "Имя", registrant.getName());
        addItem(container, "Отчество", registrant.getPatronymic());
        addItem(container, "Email", registrant.getEmail());
        addItem(container, "Группа", registrant.getGroup().toString());
        addItem(container, "Роль", registrant.getRole());
        return container;
    }

    private void addItem(IndexedContainer container, String key, String value) {
        Item item = container.addItem(key);
        item.getItemProperty(KEY_PROPERTY).setValue(key);
        item.getItemProperty(VALUE_PROPERTY).setValue(value);
    }
}
