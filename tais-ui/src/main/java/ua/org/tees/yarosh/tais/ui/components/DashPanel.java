package ua.org.tees.yarosh.tais.ui.components;

import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;

/**
 * @author Timur Yarosh
 *         Date: 22.03.14
 *         Time: 0:04
 */
public class DashPanel extends CssLayout {
    public DashPanel() {
        addStyleName("layout-panel");
        setSizeFull();
    }

    public DashPanel(Button button) {
        super();
        button.addStyleName("configure");
        button.addStyleName("icon-cog");
        button.addStyleName("icon-only");
        button.addStyleName("borderless");
        button.addStyleName("small");
        addComponent(button);
    }
}
