package ua.org.tees.yarosh.tais.ui.components.layouts;

import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.VerticalLayout;

/**
 * @author Timur Yarosh
 *         Date: 22.03.14
 *         Time: 0:09
 */
public class VerticalDash extends VerticalLayout {
    public VerticalDash() {
        setSizeFull();
        setMargin(new MarginInfo(true, true, false, true));
        setSpacing(true);
    }
}
