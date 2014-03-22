package ua.org.tees.yarosh.tais.ui.core.components;

import com.vaadin.ui.CssLayout;

/**
 * @author Timur Yarosh
 *         Date: 22.03.14
 *         Time: 10:04
 */
public class SidebarMenu extends CssLayout {
    public SidebarMenu() {
        addStyleName("no-vertical-drag-hints");
        addStyleName("no-horizontal-drag-hints");
        addStyleName("menu");
        setHeight("100%");
    }
}
