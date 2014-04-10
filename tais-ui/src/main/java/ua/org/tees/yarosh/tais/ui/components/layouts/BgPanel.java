package ua.org.tees.yarosh.tais.ui.components.layouts;

import com.vaadin.ui.*;

/**
 * @author Timur Yarosh
 *         Date: 22.03.14
 *         Time: 0:14
 */
public class BgPanel extends VerticalLayout {

    private Label titleLabel;
    private HorizontalLayout buttonsLayout = new HorizontalLayout();

    public BgPanel() {
        setWidth(100, Unit.PERCENTAGE);
//        setSpacing(true);
        addStyleName("toolbar");
        titleLabel = new Label();
        titleLabel.addStyleName("h1");
//        addComponent(titleLabel);
//        setComponentAlignment(titleLabel, Alignment.TOP_CENTER);

        buttonsLayout.setSizeUndefined();
//        addComponent(buttonsLayout);
//        setComponentAlignment(buttonsLayout, Alignment.BOTTOM_CENTER);

        HorizontalLayout layout = new HorizontalLayout(titleLabel, buttonsLayout);
        layout.setWidth(100, Unit.PERCENTAGE);
        layout.setHeight(2, Unit.PERCENTAGE);
        layout.setComponentAlignment(titleLabel, Alignment.MIDDLE_LEFT);
        layout.setComponentAlignment(buttonsLayout, Alignment.MIDDLE_RIGHT);
        addComponent(layout);
    }

    public void setCaption(String caption) {
        titleLabel.setValue(caption);
    }

    public void addButtons(Button... buttons) {
        buttonsLayout.addComponents(buttons);
    }
}
