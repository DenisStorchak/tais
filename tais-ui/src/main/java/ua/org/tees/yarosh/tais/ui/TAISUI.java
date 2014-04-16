package ua.org.tees.yarosh.tais.ui;

import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.UI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.org.tees.yarosh.tais.auth.AuthManager;
import ua.org.tees.yarosh.tais.core.common.models.Registrant;
import ua.org.tees.yarosh.tais.ui.components.layouts.CommonComponent;
import ua.org.tees.yarosh.tais.ui.components.layouts.RootLayout;
import ua.org.tees.yarosh.tais.ui.core.*;
import ua.org.tees.yarosh.tais.ui.core.mvp.FactoryBasedViewProvider;
import ua.org.tees.yarosh.tais.ui.listeners.AuthListener;
import ua.org.tees.yarosh.tais.ui.listeners.LastViewSaver;
import ua.org.tees.yarosh.tais.ui.listeners.RootToDefaultViewSwitcher;
import ua.org.tees.yarosh.tais.ui.listeners.SidebarManager;
import ua.org.tees.yarosh.tais.ui.views.admin.ScheduleView;
import ua.org.tees.yarosh.tais.ui.views.admin.SettingsView;
import ua.org.tees.yarosh.tais.ui.views.admin.UserManagementView;
import ua.org.tees.yarosh.tais.ui.views.admin.UserRegistrationView;
import ua.org.tees.yarosh.tais.ui.views.common.*;
import ua.org.tees.yarosh.tais.ui.views.student.QuestionsSuiteRunnerView;
import ua.org.tees.yarosh.tais.ui.views.student.UnresolvedTasksView;
import ua.org.tees.yarosh.tais.ui.views.teacher.*;

import static ua.org.tees.yarosh.tais.core.common.dto.Roles.*;
import static ua.org.tees.yarosh.tais.ui.core.DataBinds.SessionKeys.PREVIOUS_VIEW;
import static ua.org.tees.yarosh.tais.ui.core.DataBinds.UriFragments.*;
import static ua.org.tees.yarosh.tais.ui.core.DataBinds.UriFragments.Admin.*;
import static ua.org.tees.yarosh.tais.ui.core.DataBinds.UriFragments.Student.QUESTIONS_RUNNER;
import static ua.org.tees.yarosh.tais.ui.core.DataBinds.UriFragments.Student.UNRESOLVED;
import static ua.org.tees.yarosh.tais.ui.core.DataBinds.UriFragments.Teacher.*;
import static ua.org.tees.yarosh.tais.ui.core.ViewResolver.mapDefaultView;

/**
 * @author Timur Yarosh
 *         Date: 21.03.14
 *         Time: 19:04
 */
@Theme("dashboard")
@Title("TEES Dashboard")
@Push
public class TAISUI extends UI {

    public static final Logger log = LoggerFactory.getLogger(TAISUI.class);

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        log.debug("UI initialization");

        CssLayout content = new CssLayout();
        CommonComponent commonComponent = new CommonComponent(content);
        setContent(new RootLayout(commonComponent));

        Navigator nav = new TaisNavigator(this, content);

        mapRoleViews();
        setUpViews(nav);
        setUpListeners(nav, commonComponent);
        authenticate(nav);
    }

    private void mapRoleViews() {
        mapDefaultView(UserManagementView.class, ADMIN);
        mapDefaultView(TeacherDashboardView.class, TEACHER);
        mapDefaultView(UnresolvedTasksView.class, STUDENT);
    }

    private void setUpListeners(Navigator nav, CommonComponent commonComponent) {
        nav.addViewChangeListener(new AuthListener());
        nav.addViewChangeListener(new LastViewSaver());
        nav.addViewChangeListener(new RootToDefaultViewSwitcher());

        SidebarManager sidebarManager = SessionFactory.getCurrent().getSidebarManager();
        nav.addViewChangeListener(configureSidebarManager(sidebarManager, commonComponent));
    }

    private void setUpViews(Navigator nav) {
        nav.addProvider(new FactoryBasedViewProvider(TEACHER_DASHBOARD, TeacherDashboardView.class));
        nav.addProvider(new FactoryBasedViewProvider(USER_REGISTRATION, UserRegistrationView.class));
        nav.addProvider(new FactoryBasedViewProvider(USER_MANAGEMENT, UserManagementView.class));
        nav.addProvider(new FactoryBasedViewProvider(AUTH, LoginView.class));
        nav.addProvider(new FactoryBasedViewProvider(MANAGED_SCHEDULE, ScheduleView.class));
        nav.addProvider(new FactoryBasedViewProvider(SETTINGS, SettingsView.class));
        nav.addProvider(new FactoryBasedViewProvider(ME, Profile.class));
        nav.addProvider(new FactoryBasedViewProvider(EDIT_PROFILE, EditProfile.class));
        nav.addProvider(new FactoryBasedViewProvider(CREATE_QUESTIONS_SUITE, CreateQuestionsSuiteView.class));
        nav.addProvider(new FactoryBasedViewProvider(ENABLED_QUESTIONS, EnabledQuestionsSuitesView.class));
        nav.addProvider(new FactoryBasedViewProvider(STUDENTS, StudentsView.class));
        nav.addProvider(new FactoryBasedViewProvider(ADD_MANUAL, CreateManualTaskView.class));
        nav.addProvider(new FactoryBasedViewProvider(UNRESOLVED, UnresolvedTasksView.class));
        nav.addProvider(new FactoryBasedViewProvider(QUESTIONS_RUNNER, QuestionsSuiteRunnerView.class));
        nav.addView(ACCESS_DENIED, new AccessDeniedView());
        nav.setErrorView(new PageNotFoundView());
    }

    private void authenticate(Navigator nav) {
        getUI().access(() -> {
            Registrant auth = Registrants.getCurrent(getSession());
            if (!AuthManager.loggedIn(auth.getLogin())) {
                nav.navigateTo(AUTH);
            }
        });//
    }

    private SidebarManager configureSidebarManager(SidebarManager sidebarManager, CommonComponent commonComponent) {
        SidebarFactory sidebarFactory = SidebarFactory.createFactory(this);
        sidebarManager.setCommonComponent(commonComponent);
        sidebarManager.registerSidebar(DataBinds.UriFragments.Teacher.PREFIX, sidebarFactory.createSidebar(TEACHER));
        sidebarManager.registerSidebar(DataBinds.UriFragments.Admin.PREFIX, sidebarFactory.createSidebar(ADMIN));
        sidebarManager.registerSidebar(DataBinds.UriFragments.Student.PREFIX, sidebarFactory.createSidebar(STUDENT));
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
