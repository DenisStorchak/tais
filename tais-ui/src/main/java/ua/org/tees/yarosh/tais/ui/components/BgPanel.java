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

    private Label titleLabel;

    public BgPanel() {
        setWidth("100%");
        setSpacing(true);
        addStyleName("toolbar");
        titleLabel = new Label();
        titleLabel.setSizeUndefined();
        titleLabel.addStyleName("h1");
        addComponent(titleLabel);
        setComponentAlignment(titleLabel, Alignment.MIDDLE_LEFT);
        setExpandRatio(titleLabel, 1);
    }

    public void setCaption(String caption) {
        titleLabel.setValue(caption);
    }
}
