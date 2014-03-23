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
import ua.org.tees.yarosh.tais.ui.views.LoginView;
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
    private CommonComponent commonComponent;
    private Sidebar sidebar;
    private SidebarMenu teacherMenu = createTeacherMenu();

    private SidebarMenu createTeacherMenu() {
        SidebarMenu teacherMenu = new SidebarMenu();

        NativeButton teacherPanelButton = new NativeButton("Задания");
        teacherPanelButton.addStyleName("icon-dashboard");
        teacherMenu.addComponent(teacherPanelButton);

        NativeButton studentsListButton = new NativeButton("Студенты");
        studentsListButton.addStyleName("icon-users");
        teacherMenu.addComponent(studentsListButton);

        return teacherMenu;
    }

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        LOGGER.info("Session locale is [{}]", VaadinSession.getCurrent().getLocale());
        HelpManager helpManager = new HelpManager();
        commonComponent = new CommonComponent(content);

        sidebar = createSidebar();

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
        nav.addView(AUTH, LoginView.class);
        nav.addViewChangeListener(new SidebarManager(commonComponent, sidebar));

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

    private Sidebar createSidebar() {
        Sidebar sidebar = new Sidebar();
        sidebar.setSidebarMenu(teacherMenu);
        sidebar.setUserMenu(new UserMenu("Тимур", "Ярош"));
        return sidebar;
    }
}
