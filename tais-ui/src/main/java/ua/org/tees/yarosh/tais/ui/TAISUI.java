package ua.org.tees.yarosh.tais.ui;

import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.UI;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.context.WebApplicationContext;
import ua.org.tees.yarosh.tais.auth.AuthManager;
import ua.org.tees.yarosh.tais.core.common.models.Registrant;
import ua.org.tees.yarosh.tais.core.user.mgmt.api.service.RegistrantService;
import ua.org.tees.yarosh.tais.homework.AchievementDiaryReceptionist;
import ua.org.tees.yarosh.tais.homework.PersonalTaskHolderReceptionist;
import ua.org.tees.yarosh.tais.homework.api.HomeworkManager;
import ua.org.tees.yarosh.tais.homework.api.persistence.AchievementDiaryRepository;
import ua.org.tees.yarosh.tais.homework.api.persistence.PersonalTaskHolderRepository;
import ua.org.tees.yarosh.tais.schedule.api.DisciplineService;
import ua.org.tees.yarosh.tais.ui.components.layouts.CommonComponent;
import ua.org.tees.yarosh.tais.ui.components.layouts.RootLayout;
import ua.org.tees.yarosh.tais.ui.core.SidebarFactory;
import ua.org.tees.yarosh.tais.ui.core.TaisNavigator;
import ua.org.tees.yarosh.tais.ui.core.UIFactory;
import ua.org.tees.yarosh.tais.ui.core.ViewResolver;
import ua.org.tees.yarosh.tais.ui.core.api.DataBinds;
import ua.org.tees.yarosh.tais.ui.core.api.Registrants;
import ua.org.tees.yarosh.tais.ui.core.mvp.FactoryBasedViewProvider;
import ua.org.tees.yarosh.tais.ui.core.mvp.TaisView;
import ua.org.tees.yarosh.tais.ui.listeners.LastViewSaver;
import ua.org.tees.yarosh.tais.ui.listeners.RootToDefaultViewSwitcher;
import ua.org.tees.yarosh.tais.ui.listeners.SidebarManager;
import ua.org.tees.yarosh.tais.ui.listeners.ViewAccessGuard;
import ua.org.tees.yarosh.tais.ui.listeners.backend.ChatListener;
import ua.org.tees.yarosh.tais.ui.listeners.backend.LoginButtonsInitializer;
import ua.org.tees.yarosh.tais.ui.listeners.backend.TaskEnabledListener;
import ua.org.tees.yarosh.tais.ui.views.admin.UserManagementView;
import ua.org.tees.yarosh.tais.ui.views.common.PageNotFoundView;
import ua.org.tees.yarosh.tais.ui.views.student.UnresolvedTasksView;
import ua.org.tees.yarosh.tais.ui.views.teacher.TeacherDashboardView;
import ua.org.tees.yarosh.tais.user.comm.ChatService;

import java.util.Set;

import static org.springframework.web.context.support.WebApplicationContextUtils.getRequiredWebApplicationContext;
import static ua.org.tees.yarosh.tais.core.common.dto.Roles.*;
import static ua.org.tees.yarosh.tais.ui.core.ViewResolver.mapDefaultView;
import static ua.org.tees.yarosh.tais.ui.core.api.DataBinds.SessionKeys.PREVIOUS_VIEW;
import static ua.org.tees.yarosh.tais.ui.core.api.DataBinds.UriFragments.AUTH;

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
    private static final String VIEWS_PACKAGE = "ua.org.tees.yarosh.tais.ui.views";

    public TAISUI() {
        log.debug("UI constructed");
    }

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
        setUpUIListeners(nav, commonComponent);
        setUpBackendListeners();
    }

    private void setUpBackendListeners() {
        WebApplicationContext ctx = getRequiredWebApplicationContext(VaadinServlet.getCurrent().getServletContext());
        HomeworkManager homeworkManager = ctx.getBean(HomeworkManager.class);
        homeworkManager.addManualTaskEnabledListener(new TaskEnabledListener(this));

        RegistrantService registrantService = ctx.getBean(RegistrantService.class);
        AchievementDiaryRepository achievementDiaryRepository = ctx.getBean(AchievementDiaryRepository.class);
        registrantService.addRegistrationListener(new AchievementDiaryReceptionist(achievementDiaryRepository));

        PersonalTaskHolderRepository personalTaskHolderRepository = ctx.getBean(PersonalTaskHolderRepository.class);
        registrantService.addRegistrationListener(new PersonalTaskHolderReceptionist(personalTaskHolderRepository));

        AuthManager authManager = ctx.getBean(AuthManager.class);
        DisciplineService disciplineService = ctx.getBean(DisciplineService.class);
        authManager.addLoginListener(new LoginButtonsInitializer(this,
                registrantService, homeworkManager, disciplineService));

        ChatListener chatListener = new ChatListener(VaadinSession.getCurrent(), VaadinServlet.getCurrent(), this);
        ctx.getBean(ChatService.class).addListener(chatListener);
    }

    private void setUpUIListeners(Navigator nav, CommonComponent commonComponent) {
        nav.addViewChangeListener(new ViewAccessGuard());
        nav.addViewChangeListener(new LastViewSaver());
        nav.addViewChangeListener(new RootToDefaultViewSwitcher());

        SidebarManager sidebarManager = UIFactory.getCurrent().getSidebarManager();
        nav.addViewChangeListener(configureSidebarManager(sidebarManager, commonComponent));
    }

    private void setUpViews(Navigator nav) {
        Set<Class<?>> views = new Reflections(VIEWS_PACKAGE).getTypesAnnotatedWith(TaisView.class); //todo reflections to singleton
        for (Class<?> view : views) {
            String name = view.getAnnotation(Qualifier.class).value();
            nav.addProvider(new FactoryBasedViewProvider(name, (Class<? extends View>) view));
        }
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
}
