package ua.org.tees.yarosh.tais.ui.core.components;

import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;

/**
 * @author Timur Yarosh
 *         Date: 22.03.14
 *         Time: 11:39
 */
public class CommonComponent extends HorizontalLayout {
    public CommonComponent(CssLayout content) {
        setSizeFull();
        addStyleName("main-view");

        addComponent(content);
        content.setSizeFull();
        content.addStyleName("view-content");
        setExpandRatio(content, 1);
    }
}
