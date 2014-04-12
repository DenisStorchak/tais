package ua.org.tees.yarosh.tais.ui.listeners;

import com.google.common.eventbus.Subscribe;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinSession;
import ua.org.tees.yarosh.tais.homework.events.*;
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
public class SidebarManager implements ViewChangeListener {

    private CommonComponent commonComponent;
    private Sidebar sidebar;
    private Map<String, SidebarMenu> menus = new HashMap<>();
    private Map<String, Sidebar> sidebarPool = new HashMap<>();
    private List<String> hideExceptions = new ArrayList<>();

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

    @Override
    public void afterViewChange(ViewChangeEvent event) {
    }

    @Subscribe
    public void onManualTaskRegisteredEvent(ManualTaskRegisteredEvent event) {
        //fixme
    }

    @Subscribe
    public void onManualTaskRemovedEvent(ManualTaskRemovedEvent event) {
        //fixme
    }

    @Subscribe
    public void onManualTaskResolvedEvent(ManualTaskResolvedEvent event) {
        //fixme
    }

    @Subscribe
    public void onQuestionsSuiteRegisteredEvent(QuestionsSuiteRegisteredEvent event) {
        //fixme
    }

    @Subscribe
    public void onQuestionsSuiteRemovedEvent(QuestionsSuiteRemovedEvent event) {
        //fixme
    }

    @Subscribe
    public void onQuestionsSuiteResolvedEvent(QuestionsSuiteResolvedEvent event) {
        //fixme
    }

    @Subscribe
    public void onReportRatedEvent(ReportRatedEvent event) {
        //fixme
    }
}
