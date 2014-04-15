package ua.org.tees.yarosh.tais.ui.listeners;

import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ua.org.tees.yarosh.tais.ui.components.layouts.CommonComponent;
import ua.org.tees.yarosh.tais.ui.components.layouts.Sidebar;
import ua.org.tees.yarosh.tais.ui.components.layouts.SidebarMenu;
import ua.org.tees.yarosh.tais.ui.core.ViewResolver;

import java.util.*;

import static ua.org.tees.yarosh.tais.ui.core.DataBinds.SessionKeys.REGISTRANT_ID;

/**
 * @author Timur Yarosh
 *         Date: 22.03.14
 *         Time: 22:53
 */
@Component
@Scope("prototype")
public class SidebarManager implements ViewChangeListener {

    public static final Logger log = LoggerFactory.getLogger(SidebarManager.class);
    private CommonComponent commonComponent;

    private Sidebar sidebar;

    private Map<String, SidebarMenu> menus = new HashMap<>();
    private Map<String, Sidebar> sidebarPool = new HashMap<>();
    private List<String> hideExceptions = new ArrayList<>();

    public void setCommonComponent(CommonComponent commonComponent) {
        this.commonComponent = commonComponent;
    }

    public void setSidebar(Sidebar sidebar) {
        this.sidebar = sidebar;
    }

    public Sidebar getSidebar() {
        return sidebar;
    }

    public void registerMenu(String viewPrefix, SidebarMenu menu) {
        menus.put(viewPrefix, menu);
    }

    public void registerSidebar(String viewPrefix, Sidebar sidebar) {
        sidebarPool.put(viewPrefix, sidebar);
    }

    /**
     * Don't hide sidebar while view changing to
     *
     * @param viewName exception view name
     */
    public void addHideException(String viewName) {
        hideExceptions.add(viewName);
    }

    @Override
    public boolean beforeViewChange(ViewChangeEvent event) {
        resolveOrHideSidebar(event);
        return true;
    }

    @Override
    public void afterViewChange(ViewChangeEvent event) {

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
        this.setSidebar(resolvedSidebar);
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
        if (first.isPresent() && ViewResolver.viewRegistered(viewPattern)) {
            String properlyKey = first.get();
            Sidebar relativeSidebar = sidebarPool.get(properlyKey);
            relativeSidebar.getUserMenu().setUsername((String) VaadinSession.getCurrent().getAttribute(REGISTRANT_ID)); //todo why it works?
            return relativeSidebar;
        } else if (hideExceptions.contains(viewPattern)) {
            return sidebar;
        }
        return null;
    }
}
