package ua.org.tees.yarosh.tais.ui.core;

import com.vaadin.navigator.ViewChangeListener;
import ua.org.tees.yarosh.tais.ui.components.CommonComponent;
import ua.org.tees.yarosh.tais.ui.components.Sidebar;
import ua.org.tees.yarosh.tais.ui.components.SidebarMenu;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author Timur Yarosh
 *         Date: 22.03.14
 *         Time: 22:53
 */
public class SidebarManager implements ViewChangeListener {

    private CommonComponent commonComponent;
    private Sidebar sidebar;
    private Map<String, SidebarMenu> menus = new HashMap<>();
    private Map<String, Sidebar> sidebarPool = new HashMap<>();

    public SidebarManager(CommonComponent commonComponent, Sidebar sidebar) {
        this.commonComponent = commonComponent;
        this.sidebar = sidebar;
    }

    public void registerMenu(String viewPrefix, SidebarMenu menu) {
        menus.put(viewPrefix, menu);
    }

    public void registerSidebar(String viewPrefix, Sidebar sidebar) {
        sidebarPool.put(viewPrefix, sidebar);
    }

    @Override
    public boolean beforeViewChange(ViewChangeEvent event) {
        resolveOrHideSidebar(event);
        return true;
    }

    private void resolveOrHideSidebar(ViewChangeEvent event) {
        Sidebar resolvedSidebar = localResolveSidebar(event.getViewName());
        if (resolvedSidebar == null && sidebar != null) {
            commonComponent.removeComponent(sidebar);
        } else if (resolvedSidebar != null && sidebar != null && !resolvedSidebar.equals(sidebar)) {
            commonComponent.removeComponent(sidebar);
        }
        if (resolvedSidebar != null) {
            commonComponent.addComponentAsFirst(resolvedSidebar);
        }
        this.sidebar = resolvedSidebar;
        if (sidebar != null) {
            sidebar.getSidebarMenu().selectButton(event.getNewView().getClass());
        }
    }

    @Deprecated
    private SidebarMenu resolveMenu(String viewPattern) {
        Optional<String> first = menus.keySet().stream().filter(viewPattern::startsWith).findFirst();
        if (first.isPresent()) {
            String properlyKey = first.get();
            return menus.get(properlyKey);
        }
        return null;
    }

    private Sidebar localResolveSidebar(String viewPattern) {
        Optional<String> first = sidebarPool.keySet().stream().filter(viewPattern::startsWith).findFirst();
        if (first.isPresent()) {
            String properlyKey = first.get();
            return sidebarPool.get(properlyKey);
        }
        return null;
    }

    @Override
    public void afterViewChange(ViewChangeEvent event) {
    }
}
