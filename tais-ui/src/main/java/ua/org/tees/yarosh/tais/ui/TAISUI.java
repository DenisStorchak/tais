package ua.org.tees.yarosh.tais.ui;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.UI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.org.tees.yarosh.tais.ui.components.CommonComponent;
import ua.org.tees.yarosh.tais.ui.core.SidebarFactory;
import ua.org.tees.yarosh.tais.ui.core.SidebarManager;
import ua.org.tees.yarosh.tais.ui.core.TaisNavigator;
import ua.org.tees.yarosh.tais.ui.core.mvp.FactoryBasedViewProvider;
import ua.org.tees.yarosh.tais.ui.core.text.UriFragments.Admin;
import ua.org.tees.yarosh.tais.ui.core.text.UriFragments.Teacher;
import ua.org.tees.yarosh.tais.ui.views.AccessDeniedView;
import ua.org.tees.yarosh.tais.ui.views.LoginView;
import ua.org.tees.yarosh.tais.ui.views.PageNotFoundView;
import ua.org.tees.yarosh.tais.ui.views.admin.ScheduleView;
import ua.org.tees.yarosh.tais.ui.views.admin.UserManagementView;
import ua.org.tees.yarosh.tais.ui.views.admin.UserRegistrationView;
import ua.org.tees.yarosh.tais.ui.views.teacher.TeacherDashboardView;

import static ua.org.tees.yarosh.tais.core.common.dto.Role.ADMIN;
import static ua.org.tees.yarosh.tais.core.common.dto.Role.TEACHER;
import static ua.org.tees.yarosh.tais.ui.core.text.SessionKeys.REGISTRANT_ID;
import static ua.org.tees.yarosh.tais.ui.core.text.UriFragments.ACCESS_DENIED;
import static ua.org.tees.yarosh.tais.ui.core.text.UriFragments.AUTH;
import static ua.org.tees.yarosh.tais.ui.core.text.UriFragments.Admin.*;
import static ua.org.tees.yarosh.tais.ui.core.text.UriFragments.Teacher.TEACHER_DASHBOARD;

/**
 * @author Timur Yarosh
 *         Date: 21.03.14
 *         Time: 19:04
 */
@Theme("dashboard")
@Title("TEES Dashboard")
public class TAISUI extends UI {

    public static final Logger LOGGER = LoggerFactory.getLogger(TAISUI.class);

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        LOGGER.debug("UI initialization");
        CssLayout content = new CssLayout();
        CommonComponent commonComponent = new CommonComponent(content);
        Navigator nav = new TaisNavigator(this, content);

        nav.addView(ACCESS_DENIED, new AccessDeniedView());
        nav.addProvider(new FactoryBasedViewProvider(TEACHER_DASHBOARD, TeacherDashboardView.class));
        nav.addProvider(new FactoryBasedViewProvider(USER_REGISTRATION, UserRegistrationView.class));
        nav.addProvider(new FactoryBasedViewProvider(USER_MANAGEMENT, UserManagementView.class));
        nav.addProvider(new FactoryBasedViewProvider(AUTH, LoginView.class));
        nav.addProvider(new FactoryBasedViewProvider(MANAGED_SCHEDULE, ScheduleView.class));
        nav.setErrorView(new PageNotFoundView());
        nav.addViewChangeListener(new AuthListener());

        SidebarManager sidebarManager = new SidebarManager(commonComponent, null);
        nav.addViewChangeListener(configureSidebarManager(sidebarManager));

        CssLayout root = new CssLayout();
        setContent(root);
        root.addStyleName("root");
        root.setSizeFull();
        root.addComponent(commonComponent);

        if (VaadinSession.getCurrent().getAttribute(REGISTRANT_ID) == null) {
            nav.navigateTo(AUTH);
        }
    }

    private SidebarManager configureSidebarManager(SidebarManager sidebarManager) {
        SidebarFactory sidebarFactory = SidebarFactory.createFactory(this);
        sidebarManager.registerSidebar(Teacher.PREFIX, sidebarFactory.createSidebar(TEACHER));
        sidebarManager.registerSidebar(Admin.PREFIX, sidebarFactory.createSidebar(ADMIN));
        return sidebarManager;
    }
}
