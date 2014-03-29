package ua.org.tees.yarosh.tais.ui.components;

import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.HorizontalLayout;

/**
 * @author Timur Yarosh
 *         Date: 22.03.14
 *         Time: 0:09
 */
public class HorizontalDash extends HorizontalLayout {
    public HorizontalDash() {
        setSizeFull();
        setMargin(new MarginInfo(true, true, false, true));
        setSpacing(true);
    }
}
