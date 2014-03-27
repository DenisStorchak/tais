package ua.org.tees.yarosh.tais.ui;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.NativeButton;
import com.vaadin.ui.UI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.org.tees.yarosh.tais.ui.components.CommonComponent;
import ua.org.tees.yarosh.tais.ui.components.Sidebar;
import ua.org.tees.yarosh.tais.ui.components.SidebarMenu;
import ua.org.tees.yarosh.tais.ui.components.UserMenu;
import ua.org.tees.yarosh.tais.ui.core.SessionKeys;
import ua.org.tees.yarosh.tais.ui.core.SidebarManager;
import ua.org.tees.yarosh.tais.ui.core.UriFragments;
import ua.org.tees.yarosh.tais.ui.core.ViewResolver;
import ua.org.tees.yarosh.tais.ui.core.mvp.FactoryBasedViewProvider;
import ua.org.tees.yarosh.tais.ui.views.AccessDeniedView;
import ua.org.tees.yarosh.tais.ui.views.LoginView;
import ua.org.tees.yarosh.tais.ui.views.PageNotFoundView;
import ua.org.tees.yarosh.tais.ui.views.admin.CreateScheduleView;
import ua.org.tees.yarosh.tais.ui.views.admin.ScheduleView;
import ua.org.tees.yarosh.tais.ui.views.admin.UserManagementView;
import ua.org.tees.yarosh.tais.ui.views.admin.UserRegistrationView;
import ua.org.tees.yarosh.tais.ui.views.teacher.TeacherDashboardView;

import static ua.org.tees.yarosh.tais.ui.core.SessionKeys.REGISTRANT_ID;
import static ua.org.tees.yarosh.tais.ui.core.UriFragments.ACCESS_DENIED;
import static ua.org.tees.yarosh.tais.ui.core.UriFragments.AUTH;
import static ua.org.tees.yarosh.tais.ui.core.UriFragments.Admin.*;
import static ua.org.tees.yarosh.tais.ui.core.UriFragments.Teacher.TEACHER_DASHBOARD;

/**
 * @author Timur Yarosh
 *         Date: 21.03.14
 *         Time: 19:04
 */
@Theme("dashboard")
@Title("TEES Dashboard")
public class TAISUI extends UI {

    public static final Logger LOGGER = LoggerFactory.getLogger(TAISUI.class);

    private CssLayout root = new CssLayout();
    private CssLayout content = new CssLayout();

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        CommonComponent commonComponent = new CommonComponent(content);

        Navigator nav = new Navigator(this, content) {

            private ViewResolver viewResolver = new ViewResolver();

            @Override
            public void addView(String viewName, View view) {
                viewResolver.register(view.getClass(), viewName);
                super.addView(viewName, view);
            }

            @Override
            public void addProvider(com.vaadin.navigator.ViewProvider provider) {
                if (provider instanceof ClassBasedViewProvider) {
                    ClassBasedViewProvider classBasedViewProvider = (ClassBasedViewProvider) provider;
                    viewResolver.register(classBasedViewProvider.getViewClass(), classBasedViewProvider.getViewName());
                }
                super.addProvider(provider);
            }
        };

        nav.addView(ACCESS_DENIED, new AccessDeniedView());
        nav.addProvider(new FactoryBasedViewProvider(TEACHER_DASHBOARD, TeacherDashboardView.class));
        nav.addProvider(new FactoryBasedViewProvider(USER_REGISTRATION, UserRegistrationView.class));
        nav.addProvider(new FactoryBasedViewProvider(USER_MANAGEMENT, UserManagementView.class));
        nav.addProvider(new FactoryBasedViewProvider(AUTH, LoginView.class));
        nav.addProvider(new FactoryBasedViewProvider(MANAGED_SCHEDULE, ScheduleView.class));
        nav.addProvider(new FactoryBasedViewProvider(CREATE_SCHEDULE, CreateScheduleView.class));
        nav.setErrorView(new PageNotFoundView());

        nav.addViewChangeListener(new AuthListener());

        SidebarManager sidebarManager = new SidebarManager(commonComponent, null);
        sidebarManager.registerSidebar(UriFragments.Teacher.PREFIX, createTeacherSidebar());
        sidebarManager.registerSidebar(UriFragments.Admin.PREFIX, createAdminSidebar());

        nav.addViewChangeListener(sidebarManager);

        setContent(root);
        root.addStyleName("root");
        root.setSizeFull();
        root.addComponent(commonComponent);

        if (VaadinSession.getCurrent().getAttribute(REGISTRANT_ID) == null) {
            nav.navigateTo(AUTH);
        }
    }

    private Sidebar createTeacherSidebar() {
        Sidebar sidebar = new Sidebar();
        sidebar.setSidebarMenu(createTeacherMenu());
        sidebar.setUserMenu(createUserMenu());
        return sidebar;
    }

    private UserMenu createUserMenu() {
        return new UserMenu((String) VaadinSession.getCurrent().getAttribute(SessionKeys.REGISTRANT_ID));
    }

    private SidebarMenu createTeacherMenu() {
        SidebarMenu teacherMenu = new SidebarMenu();

        NativeButton dashboardButton = new NativeButton("Задания");
        dashboardButton.addStyleName("icon-columns");
        teacherMenu.addMenuButton(TeacherDashboardView.class, dashboardButton);

        NativeButton studentListButton = new NativeButton("Студенты");
        studentListButton.addStyleName("icon-users");
        teacherMenu.addMenuButton(null, studentListButton); // todo set related view class

        return teacherMenu;
    }

    private Sidebar createAdminSidebar() {
        Sidebar sidebar = new Sidebar();
        sidebar.setSidebarMenu(createAdminMenu());
        sidebar.setUserMenu(createUserMenu());
        return sidebar;
    }

    private SidebarMenu createAdminMenu() {
        SidebarMenu adminMenu = new SidebarMenu();

        NativeButton registrationButton = new NativeButton("Регистрация");
        registrationButton.addStyleName("icon-user-add");
        adminMenu.addMenuButton(UserRegistrationView.class, registrationButton);
        registrationButton.addClickListener(event -> getNavigator().navigateTo(USER_REGISTRATION));

        NativeButton profilesButton = new NativeButton("Пользователи");
        profilesButton.addStyleName("icon-users");
        adminMenu.addMenuButton(UserManagementView.class, profilesButton);
        profilesButton.addClickListener(event -> getNavigator().navigateTo(USER_MANAGEMENT));

        NativeButton scheduleButton = new NativeButton("Расписание");
        scheduleButton.addStyleName("icon-clock");
        adminMenu.addMenuButton(ScheduleView.class, scheduleButton);
        scheduleButton.addClickListener(event -> getNavigator().navigateTo(MANAGED_SCHEDULE));

        NativeButton fprintScannerButton = new NativeButton("Сканеры отпечатков");
        fprintScannerButton.addStyleName("icon-fingerprint_picture");
        adminMenu.addMenuButton(null, fprintScannerButton); // todo set related view class

        NativeButton configButton = new NativeButton("Настройки");
        configButton.addStyleName("icon-cog-alt");
        adminMenu.addMenuButton(null, configButton); // todo set related view class

        return adminMenu;
    }
}
