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
import ua.org.tees.yarosh.tais.ui.core.*;
import ua.org.tees.yarosh.tais.ui.core.components.CommonComponent;
import ua.org.tees.yarosh.tais.ui.core.components.Sidebar;
import ua.org.tees.yarosh.tais.ui.core.components.SidebarMenu;
import ua.org.tees.yarosh.tais.ui.core.components.UserMenu;
import ua.org.tees.yarosh.tais.ui.core.mvp.ViewProvider;
import ua.org.tees.yarosh.tais.ui.views.LoginPresenterBasedView;
import ua.org.tees.yarosh.tais.ui.views.admin.UserRegistrationView;
import ua.org.tees.yarosh.tais.ui.views.teacher.TeacherDashboardView;

import static ua.org.tees.yarosh.tais.ui.core.Messages.WELCOME_MESSAGE;
import static ua.org.tees.yarosh.tais.ui.core.SessionKeys.REGISTRANT_ID;
import static ua.org.tees.yarosh.tais.ui.core.UriFragments.AUTH;
import static ua.org.tees.yarosh.tais.ui.core.UriFragments.Admin.USER_REGISTRATION;
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
        LOGGER.info("Session locale is [{}]", VaadinSession.getCurrent().getLocale());
        HelpManager helpManager = new HelpManager();
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
                ClassBasedViewProvider classBasedViewProvider = (ClassBasedViewProvider) provider;
                viewResolver.register(classBasedViewProvider.getViewClass(), classBasedViewProvider.getViewName());
                super.addProvider(provider);
            }
        };
        nav.addProvider(new ViewProvider(TEACHER_DASHBOARD, TeacherDashboardView.class, helpManager));
        nav.addProvider(new ViewProvider(USER_REGISTRATION, UserRegistrationView.class, helpManager));
        nav.addView(AUTH, LoginPresenterBasedView.class);

        SidebarManager sidebarManager = new SidebarManager(commonComponent, null);
        sidebarManager.registerSidebar(UriFragments.Teacher.PREFIX, createTeacherSidebar());
        sidebarManager.registerSidebar(UriFragments.Admin.PREFIX, createAdminSidebar());

        nav.addViewChangeListener(sidebarManager);

        getSession().setConverterFactory(new MyConverterFactory());
        setLocale(VaadinSession.getCurrent().getLocale());

        setContent(root);
        root.addStyleName("root");
        root.setSizeFull();

        HelpOverlay helpOverlay = helpManager.addOverlay("TAIS", WELCOME_MESSAGE, "login");
        helpOverlay.center();
        addWindow(helpOverlay);

        root.addComponent(commonComponent);

        if (VaadinSession.getCurrent().getAttribute(REGISTRANT_ID) == null) {
            nav.navigateTo(AUTH);
        } else {
            nav.navigateTo(TEACHER_DASHBOARD);
        }
    }

    private Sidebar createTeacherSidebar() {
        Sidebar sidebar = new Sidebar();
        sidebar.setSidebarMenu(createTeacherMenu());
        sidebar.setUserMenu(createUserMenu());
        return sidebar;
    }

    private UserMenu createUserMenu() {
        return new UserMenu("Тимур", "Ярош");
    }

    private SidebarMenu createTeacherMenu() {
        SidebarMenu teacherMenu = new SidebarMenu();

        NativeButton dashboardButton = new NativeButton("Задания");
        dashboardButton.addStyleName("icon-dashboard");
        teacherMenu.addComponent(dashboardButton);

        NativeButton studentListButton = new NativeButton("Студенты");
        studentListButton.addStyleName("icon-users");
        teacherMenu.addComponent(studentListButton);

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

        NativeButton viewConfigButton = new NativeButton("Актуальные настройки");
        viewConfigButton.addStyleName("icon-cog");
        adminMenu.addComponent(viewConfigButton);

        NativeButton createRegistrationButton = new NativeButton("Регистрация пользователей");
        createRegistrationButton.addStyleName("icon-user-add");
        adminMenu.addComponent(createRegistrationButton);

        NativeButton editRegistrantButton = new NativeButton("Изменение профилей");
        editRegistrantButton.addStyleName("icon-th");
        adminMenu.addComponent(editRegistrantButton);

        NativeButton fprintScannerSettingsButton = new NativeButton("Сканеры отпечатков пальцев");
        fprintScannerSettingsButton.addStyleName("icon-fingerprint_picture");
        adminMenu.addComponent(fprintScannerSettingsButton);

        return adminMenu;
    }
}
