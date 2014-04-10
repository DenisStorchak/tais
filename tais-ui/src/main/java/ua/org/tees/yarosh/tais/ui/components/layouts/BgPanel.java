package ua.org.tees.yarosh.tais.ui.components.layouts;

import com.vaadin.ui.*;

/**
 * @author Timur Yarosh
 *         Date: 22.03.14
 *         Time: 0:14
 */
public class BgPanel extends VerticalLayout {

    private Label titleLabel;
    private HorizontalLayout additionalLayout = new HorizontalLayout();

    public BgPanel() {
        super.setWidth(100, Unit.PERCENTAGE);
        super.addStyleName("toolbar");
        titleLabel = new Label();
        titleLabel.addStyleName("h1");

        additionalLayout.setSizeUndefined();

        HorizontalLayout layout = new HorizontalLayout(titleLabel, additionalLayout);
        layout.setWidth(100, Unit.PERCENTAGE);
        layout.setHeight(2, Unit.PERCENTAGE);
        layout.setComponentAlignment(titleLabel, Alignment.MIDDLE_LEFT);
        layout.setComponentAlignment(additionalLayout, Alignment.MIDDLE_RIGHT);
        super.addComponent(layout);
    }

    public void setCaption(String caption) {
        titleLabel.setValue(caption);
    }

    @Override
    public void addComponents(Component... components) {
        additionalLayout.addComponents(components);
    }

    @Override
    public void addComponent(Component c, int index) {
        additionalLayout.addComponent(c, index);
    }

    @Override
    public void addComponentAsFirst(Component c) {
        additionalLayout.addComponentAsFirst(c);
    }

    @Override
    public void addComponent(Component c) {
        additionalLayout.addComponent(c);
    }

    @Override
    public void setSpacing(boolean spacing) {
        additionalLayout.setSpacing(spacing);
    }
}
