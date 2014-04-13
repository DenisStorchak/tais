package ua.org.tees.yarosh.tais.ui.listeners;

import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.Subscribe;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Button;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ua.org.tees.yarosh.tais.core.common.RegexUtils;
import ua.org.tees.yarosh.tais.homework.events.*;
import ua.org.tees.yarosh.tais.ui.components.layouts.CommonComponent;
import ua.org.tees.yarosh.tais.ui.components.layouts.Sidebar;
import ua.org.tees.yarosh.tais.ui.components.layouts.SidebarMenu;
import ua.org.tees.yarosh.tais.ui.core.Registrants;
import ua.org.tees.yarosh.tais.ui.core.ViewResolver;
import ua.org.tees.yarosh.tais.ui.views.student.UnresolvedTasksView;

import java.util.*;
import java.util.regex.Pattern;

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

    @Override
    public void afterViewChange(ViewChangeEvent event) {
    }

    @Subscribe
    @AllowConcurrentEvents
    public void onManualTaskRegisteredEvent(ManualTaskRegisteredEvent event) {
        log.debug("ManualTaskRegisteredEvent handler invoked");
        if (event.getTask().getStudentGroup().equals(Registrants.getCurrent().getGroup())) {
            log.debug("Groups matcher, badge value will be incremented");
            Button button = sidebar.getSidebarMenu().getButton(UnresolvedTasksView.class);
            log.debug("Old button caption is [{}]", button.getCaption());
            String badge = RegexUtils.substringMatching(button.getCaption(), Pattern.compile(".*(\\d+).*"));
            int newValue = Integer.valueOf(badge) + 1;
            button.setCaption(button.getCaption().replaceAll("\\d+", String.valueOf(newValue)));
            log.debug("New button caption is [{}]", button.getCaption());
        }
    }

    @Subscribe
    @AllowConcurrentEvents
    public void onManualTaskRemovedEvent(ManualTaskRemovedEvent event) {
        //fixme
    }

    @Subscribe
    @AllowConcurrentEvents
    public void onManualTaskResolvedEvent(ManualTaskResolvedEvent event) {
        //fixme
    }

    @Subscribe
    @AllowConcurrentEvents
    public void onQuestionsSuiteRegisteredEvent(QuestionsSuiteRegisteredEvent event) {
        //fixme
    }

    @Subscribe
    @AllowConcurrentEvents
    public void onQuestionsSuiteRemovedEvent(QuestionsSuiteRemovedEvent event) {
        //fixme
    }

    @Subscribe
    @AllowConcurrentEvents
    public void onQuestionsSuiteResolvedEvent(QuestionsSuiteResolvedEvent event) {
        //fixme
    }

    @Subscribe
    @AllowConcurrentEvents
    public void onReportRatedEvent(ReportRatedEvent event) {
        //fixme
    }
}
