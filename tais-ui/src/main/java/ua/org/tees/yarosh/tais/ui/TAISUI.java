package ua.org.tees.yarosh.tais.ui;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinService;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.UI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.org.tees.yarosh.tais.auth.AuthManager;
import ua.org.tees.yarosh.tais.ui.components.CommonComponent;
import ua.org.tees.yarosh.tais.ui.core.*;
import ua.org.tees.yarosh.tais.ui.core.mvp.FactoryBasedViewProvider;
import ua.org.tees.yarosh.tais.ui.views.admin.ScheduleView;
import ua.org.tees.yarosh.tais.ui.views.admin.SettingsView;
import ua.org.tees.yarosh.tais.ui.views.admin.UserManagementView;
import ua.org.tees.yarosh.tais.ui.views.admin.UserRegistrationView;
import ua.org.tees.yarosh.tais.ui.views.common.*;
import ua.org.tees.yarosh.tais.ui.views.teacher.CreateQuestionsSuiteView;
import ua.org.tees.yarosh.tais.ui.views.teacher.TeacherDashboardView;

import javax.servlet.http.Cookie;

import static ua.org.tees.yarosh.tais.core.common.dto.Roles.ADMIN;
import static ua.org.tees.yarosh.tais.core.common.dto.Roles.TEACHER;
import static ua.org.tees.yarosh.tais.ui.core.DataBinds.Cookies;
import static ua.org.tees.yarosh.tais.ui.core.DataBinds.SessionKeys.PREVIOUS_VIEW;
import static ua.org.tees.yarosh.tais.ui.core.DataBinds.UriFragments.*;
import static ua.org.tees.yarosh.tais.ui.core.DataBinds.UriFragments.Admin.*;
import static ua.org.tees.yarosh.tais.ui.core.DataBinds.UriFragments.Teacher.CREATE_QUESTIONS_SUITE;
import static ua.org.tees.yarosh.tais.ui.core.DataBinds.UriFragments.Teacher.TEACHER_DASHBOARD;
import static ua.org.tees.yarosh.tais.ui.core.ViewResolver.registerDefaultView;

/**
 * @author Timur Yarosh
 *         Date: 21.03.14
 *         Time: 19:04
 */
@Theme("dashboard")
@Title("TEES Dashboard")
public class TAISUI extends UI {

    public static final Logger log = LoggerFactory.getLogger(TAISUI.class);

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        log.debug("UI initialization");

        {
            registerDefaultView(UserRegistrationView.class, ADMIN);
            registerDefaultView(TeacherDashboardView.class, TEACHER);
            //todo register default student view
        }

        CssLayout content = new CssLayout();
        CommonComponent commonComponent = new CommonComponent(content);
        Navigator nav = new TaisNavigator(this, content);

        nav.addProvider(new FactoryBasedViewProvider(TEACHER_DASHBOARD, TeacherDashboardView.class));
        nav.addProvider(new FactoryBasedViewProvider(USER_REGISTRATION, UserRegistrationView.class));
        nav.addProvider(new FactoryBasedViewProvider(USER_MANAGEMENT, UserManagementView.class));
        nav.addProvider(new FactoryBasedViewProvider(AUTH, LoginView.class));
        nav.addProvider(new FactoryBasedViewProvider(MANAGED_SCHEDULE, ScheduleView.class));
        nav.addProvider(new FactoryBasedViewProvider(SETTINGS, SettingsView.class));
        nav.addProvider(new FactoryBasedViewProvider(ME, ProfileView.class));
        nav.addProvider(new FactoryBasedViewProvider(EDIT_PROFILE, EditProfileView.class));
        nav.addProvider(new FactoryBasedViewProvider(CREATE_QUESTIONS_SUITE, CreateQuestionsSuiteView.class));
        nav.addView(ACCESS_DENIED, new AccessDeniedView());
        nav.setErrorView(new PageNotFoundView());

        nav.addViewChangeListener(new AuthListener());
        nav.addViewChangeListener(new LastViewSaver());
        nav.addViewChangeListener(new RootToDefaultViewSwitcher());

        SidebarManager sidebarManager = new SidebarManager(commonComponent, null);
        nav.addViewChangeListener(configureSidebarManager(sidebarManager));
        log.debug("SidebarManager configured");

        CssLayout root = new CssLayout();
        setContent(root);
        root.addStyleName("root");
        root.setSizeFull();
        root.addComponent(commonComponent);

        Cookie[] cookies = VaadinService.getCurrentRequest().getCookies();
        boolean authorized = false;
        for (int i = 0; i < cookies.length && !authorized; i++) {
            if (cookies[i].getName().equals(Cookies.AUTH)) {
                authorized = AuthManager.loggedIn(cookies[i].getValue());
            }
        }
        if (!authorized) {
            nav.navigateTo(AUTH);
        }
    }

    private SidebarManager configureSidebarManager(SidebarManager sidebarManager) {
        SidebarFactory sidebarFactory = SidebarFactory.createFactory(this);
        sidebarManager.registerSidebar(DataBinds.UriFragments.Teacher.PREFIX, sidebarFactory.createSidebar(TEACHER));
        sidebarManager.registerSidebar(DataBinds.UriFragments.Admin.PREFIX, sidebarFactory.createSidebar(ADMIN));
        sidebarManager.addHideException(DataBinds.UriFragments.ME);
        sidebarManager.addHideException(DataBinds.UriFragments.EDIT_PROFILE);
        return sidebarManager;
    }

    public static void navigateBack() {
        View previousView = (View) VaadinSession.getCurrent().getAttribute(PREVIOUS_VIEW);
        String previousState = ViewResolver.resolveUnregistered(previousView);
        Navigator navigator = VaadinSession.getCurrent().getUIs().iterator().next().getNavigator();
        if (!navigator.getState().equals(previousState)) navigator.navigateTo(previousState);
    }

    public static void navigateTo(String state) {
        getCurrent().getNavigator().navigateTo(state);
    }
}
