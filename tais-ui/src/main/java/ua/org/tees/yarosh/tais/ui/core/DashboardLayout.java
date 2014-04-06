package ua.org.tees.yarosh.tais.ui.core;

import com.vaadin.ui.*;
import ua.org.tees.yarosh.tais.ui.components.BgPanel;
import ua.org.tees.yarosh.tais.ui.components.DashPanel;
import ua.org.tees.yarosh.tais.ui.components.HorizontalDash;
import ua.org.tees.yarosh.tais.ui.components.VerticalDash;

public abstract class DashboardLayout extends VerticalLayout {

    protected final HorizontalLayout top;
    protected final AbstractOrderedLayout dash;

    protected DashboardLayout(String caption) {
        setSizeFull();
        addStyleName("dashboard-view");

        top = new BgPanel(caption);
        addComponent(top);

        dash = new HorizontalDash();
        addComponent(dash);
        setExpandRatio(dash, 1.5f);
    }

    protected DashboardLayout(String caption, boolean horizontalDash) {
        setSizeFull();
        addStyleName("dashboard-view");

        top = new BgPanel(caption);
        addComponent(top);

        if (horizontalDash) {
            dash = new HorizontalDash();
        } else {
            dash = new VerticalDash();
        }
        addComponent(dash);
        setExpandRatio(dash, 1.5f);
    }

    protected DashPanel addDashPanel(String caption, Button configureButton, Component... components) {
        DashPanel dashPanel;
        if (configureButton == null) {
            dashPanel = new DashPanel();
        } else {
            dashPanel = new DashPanel(configureButton);
        }
        dashPanel.setCaption(caption);
        dashPanel.addComponents(components);
        dashPanel.setWidth(100, Unit.PERCENTAGE);

        dash.addComponent(dashPanel);
        return dashPanel;
    }
}
