package ua.org.tees.yarosh.tais.ui.core;

import com.vaadin.navigator.ViewChangeListener;
import ua.org.tees.yarosh.tais.ui.core.components.CommonComponent;
import ua.org.tees.yarosh.tais.ui.core.components.Sidebar;

import static ua.org.tees.yarosh.tais.ui.core.UriFragments.AUTH;

/**
 * @author Timur Yarosh
 *         Date: 22.03.14
 *         Time: 22:53
 */
public class SidebarManager implements ViewChangeListener {

    private CommonComponent commonComponent;
    private Sidebar sidebar;

    public SidebarManager(CommonComponent commonComponent, Sidebar sidebar) {
        this.commonComponent = commonComponent;
        this.sidebar = sidebar;
    }

    @Override
    public boolean beforeViewChange(ViewChangeEvent event) {
        if (event.getViewName().equals(AUTH)) {
            commonComponent.removeComponent(sidebar);
        } else {
            commonComponent.addComponentAsFirst(sidebar);
        }
        return true;
    }

    @Override
    public void afterViewChange(ViewChangeEvent event) {
    }
}
