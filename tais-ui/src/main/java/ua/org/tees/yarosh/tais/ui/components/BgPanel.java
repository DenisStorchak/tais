package ua.org.tees.yarosh.tais.ui.components;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

/**
 * @author Timur Yarosh
 *         Date: 22.03.14
 *         Time: 0:14
 */
public class BgPanel extends HorizontalLayout {
    public BgPanel(String title) {
        setWidth("100%");
        setSpacing(true);
        addStyleName("toolbar");
        Label titleLabel = new Label(title);
        titleLabel.setSizeUndefined();
        titleLabel.addStyleName("h1");
        addComponent(titleLabel);
        setComponentAlignment(titleLabel, Alignment.MIDDLE_LEFT);
        setExpandRatio(titleLabel, 1);
    }
}
