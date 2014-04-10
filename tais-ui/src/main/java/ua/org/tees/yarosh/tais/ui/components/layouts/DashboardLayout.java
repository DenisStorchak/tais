package ua.org.tees.yarosh.tais.ui.components.layouts;

import com.vaadin.ui.*;

public abstract class DashboardLayout extends VerticalLayout {

    protected final BgPanel top;
    protected final AbstractOrderedLayout dash;

    protected DashboardLayout() {
        setSizeFull();
        addStyleName("dashboard-view");

        top = new BgPanel();
        addComponent(top);
        setComponentAlignment(top, Alignment.TOP_CENTER);

        dash = new HorizontalDash();
        addComponent(dash);
        setExpandRatio(dash, 1.5f);
    }

    protected DashboardLayout(boolean horizontalDash) {
        setSizeFull();
        addStyleName("dashboard-view");

        top = new BgPanel();
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

    protected void setBackgroundCaption(String caption) {
        top.setCaption(caption);
    }
}
