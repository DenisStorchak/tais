package ua.org.tees.yarosh.tais.ui;

import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.Subscribe;
import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.UI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import ua.org.tees.yarosh.tais.auth.AuthManager;
import ua.org.tees.yarosh.tais.core.common.RegexUtils;
import ua.org.tees.yarosh.tais.core.common.models.Registrant;
import ua.org.tees.yarosh.tais.homework.api.HomeworkManager;
import ua.org.tees.yarosh.tais.homework.events.ManualTaskEnabledEvent;
import ua.org.tees.yarosh.tais.ui.components.layouts.CommonComponent;
import ua.org.tees.yarosh.tais.ui.components.layouts.RootLayout;
import ua.org.tees.yarosh.tais.ui.core.SidebarFactory;
import ua.org.tees.yarosh.tais.ui.core.TaisNavigator;
import ua.org.tees.yarosh.tais.ui.core.UIFactoryAccessor;
import ua.org.tees.yarosh.tais.ui.core.ViewResolver;
import ua.org.tees.yarosh.tais.ui.core.api.DataBinds;
import ua.org.tees.yarosh.tais.ui.core.api.LoginListener;
import ua.org.tees.yarosh.tais.ui.core.api.Registrants;
import ua.org.tees.yarosh.tais.ui.core.events.LoginEvent;
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

import java.util.regex.Pattern;

import static org.springframework.web.context.support.WebApplicationContextUtils.getRequiredWebApplicationContext;
import static ua.org.tees.yarosh.tais.core.common.dto.Roles.*;
import static ua.org.tees.yarosh.tais.homework.api.HomeworkManager.ManualTaskEnabledListenerTeacher;
import static ua.org.tees.yarosh.tais.ui.core.ViewResolver.mapDefaultView;
import static ua.org.tees.yarosh.tais.ui.core.api.DataBinds.SessionKeys.PREVIOUS_VIEW;
import static ua.org.tees.yarosh.tais.ui.core.api.DataBinds.UriFragments.*;
import static ua.org.tees.yarosh.tais.ui.core.api.DataBinds.UriFragments.Admin.*;
import static ua.org.tees.yarosh.tais.ui.core.api.DataBinds.UriFragments.Student.QUESTIONS_RUNNER;
import static ua.org.tees.yarosh.tais.ui.core.api.DataBinds.UriFragments.Student.UNRESOLVED;
import static ua.org.tees.yarosh.tais.ui.core.api.DataBinds.UriFragments.Teacher.*;

/**
 * @author Timur Yarosh
 *         Date: 21.03.14
 *         Time: 19:04
 */
@Theme("dashboard")
@Title("TEES Dashboard")
@Push
public class TAISUI extends UI implements ManualTaskEnabledListenerTeacher, LoginListener {

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

        SidebarManager sidebarManager = UIFactoryAccessor.getCurrent().getSidebarManager();
        nav.addViewChangeListener(configureSidebarManager(sidebarManager, commonComponent));

        WebApplicationContext ctx = getRequiredWebApplicationContext(VaadinServlet.getCurrent().getServletContext());
        ctx.getBean(HomeworkManager.class).addManualTaskEnabledListener(this);
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
        Registrant auth = Registrants.getCurrent(getSession());
        if (auth == null || !AuthManager.loggedIn(auth.getLogin())) {
            nav.navigateTo(AUTH);
        }
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

    @Override
    @Subscribe
    @AllowConcurrentEvents
    public void onEnabled(ManualTaskEnabledEvent event) {
        access(() -> {
            log.debug("ManualTaskEnabledEvent handler invoked");
            Registrant registrant = Registrants.getCurrent(getSession());
            if (registrant != null && event.getTask().getStudentGroup().equals(registrant.getGroup())) {
                log.debug("Registrant [{}] session affected", registrant.toString());
                Button button = UIFactoryAccessor.getCurrent().getSidebarManager().getSidebar()
                        .getSidebarMenu().getButton(UnresolvedTasksView.class);
                if (button != null) {
                    log.debug("Old button caption is [{}]", button.getCaption());
                    String badge = RegexUtils.substringMatching(button.getCaption(), Pattern.compile("(\\d+)"));
                    int newValue = Integer.valueOf(badge) + 1;
                    button.setCaption(button.getCaption().replaceAll("\\d+", String.valueOf(newValue)));
                    log.debug("New button caption is [{}]", button.getCaption());
                } else {
                    log.warn("Button not found");
                }
            } else {
                log.debug("Current registrant is null, so handler is resting");
            }
        });
    }

    @Override
    @Subscribe
    @AllowConcurrentEvents
    public void onLogin(LoginEvent event) {
        access(() -> {
            log.debug("LoginEvent handler invoked");
            Registrant registrant = event.getRegistrant();
            if (registrant.getRole().equals(STUDENT)) {
                log.debug("Registrant [{}] session affected", registrant.toString());

                WebApplicationContext ctx = getRequiredWebApplicationContext(VaadinServlet.getCurrent().getServletContext());
                HomeworkManager homeworkManager = ctx.getBean(HomeworkManager.class);

                int unresolvedManual = homeworkManager.findUnresolvedActualManualTasks(event.getRegistrant()).size();
                int unresolvedSuites = homeworkManager.findUnresolvedActualQuestionsSuite(event.getRegistrant()).size();
                int tasks = unresolvedManual + unresolvedSuites;

                setUpUnresolvedTasksButton(tasks);
            } else {
                log.debug("Current registrant is null, so handler is resting");
            }
        });
    }

    private void setUpUnresolvedTasksButton(int tasks) {
        Button button = UIFactoryAccessor.getCurrent().getSidebarManager().getSidebar()
                .getSidebarMenu().getButton(UnresolvedTasksView.class);
        if (button != null) {
            log.debug("Old button caption is [{}]", button.getCaption());
            button.setCaption(button.getCaption().replaceAll("\\d+", String.valueOf(tasks)));
            log.debug("New button caption is [{}]", button.getCaption());
        } else {
            log.warn("Button not found");
        }
    }
}
