package ua.org.tees.yarosh.tais.ui.roles;

import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

/**
 * @author Timur Yarosh
 *         Date: 21.03.14
 *         Time: 21:36
 */
public class Sidebar extends HorizontalLayout {

    public Sidebar() {
        setSizeFull();
        addStyleName("main-view");
        addComponent(new VerticalLayout() {
            {
                addStyleName("sidebar");
                setWidth(null);
                setHeight("100%");
                addComponent(new SidebarLogo());
            }
        });
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
