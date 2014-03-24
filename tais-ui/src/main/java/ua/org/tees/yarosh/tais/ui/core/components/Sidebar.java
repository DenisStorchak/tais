package ua.org.tees.yarosh.tais.ui.core.components;

import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

/**
 * @author Timur Yarosh
 *         Date: 21.03.14
 *         Time: 21:36
 */
public class Sidebar extends VerticalLayout {

    private SidebarMenu sidebarMenu;
    private UserMenu userMenu;

    public Sidebar() {
        addStyleName("sidebar");
        setWidth(null);
        setHeight("100%");
        addComponent(new SidebarLogo());
    }

    public void setSidebarMenu(SidebarMenu sidebarMenu) {
        if (this.sidebarMenu != null && sidebarMenu != null) {
            replaceComponent(this.sidebarMenu, sidebarMenu);
            setExpandRatio(sidebarMenu, 1);
            this.sidebarMenu = sidebarMenu;
        } else if (this.sidebarMenu != null) {
            this.sidebarMenu.setVisible(false);
        } else if (sidebarMenu != null) {
            addComponent(sidebarMenu);
            setExpandRatio(sidebarMenu, 1);
            this.sidebarMenu = sidebarMenu;
        }
    }

    public SidebarMenu getSidebarMenu() {
        return sidebarMenu;
    }

    public void setUserMenu(UserMenu userMenu) {
        if (this.userMenu != null && userMenu != null) {
            replaceComponent(this.userMenu, userMenu);
            this.userMenu = userMenu;
        } else if (this.userMenu != null) {
            this.userMenu.setVisible(false);
        } else if (userMenu != null) {
            addComponent(userMenu);
            this.userMenu = userMenu;
        }
    }

    public static class SidebarLogo extends CssLayout {
        public SidebarLogo() {
            addStyleName("branding");
            Label logo = new Label("<span>TEES</span> Dashboard", ContentMode.HTML);
            logo.setSizeUndefined();
            addComponent(logo);
        }
    }
}
