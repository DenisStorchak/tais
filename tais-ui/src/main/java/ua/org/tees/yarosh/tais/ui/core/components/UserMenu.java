package ua.org.tees.yarosh.tais.ui.core.components;

import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.*;
import ua.org.tees.yarosh.tais.ui.core.UriFragments;

import static ua.org.tees.yarosh.tais.ui.core.SessionKeys.REGISTRANT_ID;


/**
 * @author Timur Yarosh
 *         Date: 22.03.14
 *         Time: 11:13
 */
public class UserMenu extends VerticalLayout {
    public UserMenu(String name, String surname) {
        setSizeUndefined();
        addStyleName("user");

        Image profilePicture = new Image(null, new ThemeResource("img/profile-pic.png"));
        profilePicture.setWidth("34px");
        addComponent(profilePicture);

        Label username = new Label(name + " " + surname);
        username.setSizeUndefined();
        addComponent(username);

        MenuBar.Command notImplementedCommand = menuItem -> Notification.show("Not implemented yet");
        MenuBar settings = new MenuBar();
        MenuBar.MenuItem settingsMenu = settings.addItem("", null);
        settingsMenu.setStyleName("icon-cog-alt");
        settingsMenu.addItem("Профиль", notImplementedCommand);
        settingsMenu.addItem("Настройки профиля", notImplementedCommand);
        addComponent(settings);

        Button signOut = new NativeButton("Sign Out");
        signOut.addStyleName("icon-logout");
        signOut.setDescription("Выход");
        signOut.addClickListener(event -> {
            VaadinSession.getCurrent().setAttribute(REGISTRANT_ID, null);
            getUI().getNavigator().navigateTo(UriFragments.AUTH);
        });
        addComponent(signOut);
    }
}
