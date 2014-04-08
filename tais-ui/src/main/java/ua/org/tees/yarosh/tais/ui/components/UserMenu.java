package ua.org.tees.yarosh.tais.ui.components;

import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;
import org.springframework.web.context.support.WebApplicationContextUtils;
import ua.org.tees.yarosh.tais.auth.AuthManager;
import ua.org.tees.yarosh.tais.ui.core.SessionFactory;

import static com.vaadin.server.VaadinSession.getCurrent;
import static ua.org.tees.yarosh.tais.ui.core.DataBinds.SessionKeys.REGISTRANT_ID;
import static ua.org.tees.yarosh.tais.ui.core.DataBinds.UriFragments.*;
import static ua.org.tees.yarosh.tais.ui.core.ViewResolver.resolveView;
import static ua.org.tees.yarosh.tais.ui.views.common.api.EditProfileTaisView.EditProfilePresenter;


/**
 * @author Timur Yarosh
 *         Date: 22.03.14
 *         Time: 11:13
 */
public class UserMenu extends VerticalLayout {

    private Label username;

    public UserMenu(String registrantId) {
        setSizeUndefined();
        addStyleName("user");

        Image profilePicture = new Image(null, new ThemeResource("img/profile-pic.png"));
        profilePicture.setWidth("34px");
        addComponent(profilePicture);

        username = new Label(registrantId);
        username.setSizeUndefined();
        addComponent(username);

        MenuBar settings = new MenuBar();
        MenuBar.MenuItem settingsMenu = settings.addItem("", null);
        settingsMenu.setStyleName("icon-cog-alt");
        settingsMenu.addItem("Профиль", menuItem -> getUI().getNavigator().navigateTo(ME));
        settingsMenu.addItem("Редактировать профиль", menuItem -> {
            getUI().getNavigator().navigateTo(EDIT_PROFILE);
            EditProfilePresenter presenter = SessionFactory.getCurrent()
                    .getRelativePresenter(resolveView(EDIT_PROFILE), EditProfilePresenter.class);
            String login = (String) getCurrent().getAttribute(REGISTRANT_ID);
            presenter.setRegistrantId(login);
            presenter.update();
        });
        addComponent(settings);

        Button signOut = new NativeButton("Sign Out");
        signOut.addStyleName("icon-logout");
        signOut.setDescription("Выход");
        signOut.addClickListener(event -> {
            WebApplicationContextUtils.getRequiredWebApplicationContext(VaadinServlet.getCurrent().getServletContext())
                    .getBean(AuthManager.class).logout(username.getValue());
            getUI().getNavigator().navigateTo(AUTH);
        });
        addComponent(signOut);
    }

    public void setUsername(String username) {
        this.username.setValue(username);
    }
}
