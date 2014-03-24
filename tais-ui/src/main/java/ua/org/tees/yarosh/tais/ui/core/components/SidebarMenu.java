package ua.org.tees.yarosh.tais.ui.core.components;

import com.vaadin.navigator.View;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Timur Yarosh
 *         Date: 22.03.14
 *         Time: 10:04
 */
public class SidebarMenu extends CssLayout {

    private static final String SELECTED_BUTTON_STYLE = "selected";
    private Map<Class<? extends View>, Button> buttons = new HashMap<>();

    public SidebarMenu() {
        addStyleName("no-vertical-drag-hints");
        addStyleName("no-horizontal-drag-hints");
        addStyleName("menu");
        setHeight("100%");
    }

    public void addMenuButton(Class<? extends View> clazz, Button menuButton) {
        buttons.put(clazz, menuButton);
        addComponent(menuButton);
    }

    public void selectButton(Class<? extends View> clazz) {
        buttons.values().forEach(b -> b.removeStyleName(SELECTED_BUTTON_STYLE));
        buttons.get(clazz).addStyleName(SELECTED_BUTTON_STYLE);
    }
}
