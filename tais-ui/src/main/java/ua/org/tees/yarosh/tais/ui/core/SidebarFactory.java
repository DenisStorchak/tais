package ua.org.tees.yarosh.tais.ui.core;

import com.vaadin.server.VaadinSession;
import com.vaadin.ui.NativeButton;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.org.tees.yarosh.tais.core.common.dto.Roles;
import ua.org.tees.yarosh.tais.ui.components.Sidebar;
import ua.org.tees.yarosh.tais.ui.components.SidebarMenu;
import ua.org.tees.yarosh.tais.ui.components.UserMenu;
import ua.org.tees.yarosh.tais.ui.views.admin.ScheduleView;
import ua.org.tees.yarosh.tais.ui.views.admin.SettingsView;
import ua.org.tees.yarosh.tais.ui.views.admin.UserManagementView;
import ua.org.tees.yarosh.tais.ui.views.admin.UserRegistrationView;
import ua.org.tees.yarosh.tais.ui.views.teacher.CreateQuestionsSuiteView;
import ua.org.tees.yarosh.tais.ui.views.teacher.TeacherDashboardView;

import static ua.org.tees.yarosh.tais.ui.core.DataBinds.SessionKeys.REGISTRANT_ID;
import static ua.org.tees.yarosh.tais.ui.core.DataBinds.UriFragments.Admin.*;
import static ua.org.tees.yarosh.tais.ui.core.DataBinds.UriFragments.Teacher.CREATE_QUESTIONS_SUITE;
import static ua.org.tees.yarosh.tais.ui.core.DataBinds.UriFragments.Teacher.TEACHER_DASHBOARD;

public class SidebarFactory {

    private static final Logger log = LoggerFactory.getLogger(SidebarFactory.class);
    private UI ui;

    public static SidebarFactory createFactory(UI ui) {
        return new SidebarFactory(ui);
    }

    private SidebarFactory(UI ui) {
        this.ui = ui;
    }

    public Sidebar createSidebar(String role) {
        switch (role) {
            case Roles.ADMIN:
                return createAdminSidebar();
            case Roles.TEACHER:
                return createTeacherSidebar();
        }
        throw new IllegalArgumentException(String.format("Sidebar for role [%s] not found", role));
    }

    private Sidebar createTeacherSidebar() {
        Sidebar sidebar = new Sidebar();
        sidebar.setSidebarMenu(createTeacherMenu());
        sidebar.setUserMenu(createUserMenu());
        return sidebar;
    }

    private UserMenu createUserMenu() {
        log.debug("User menu will be created");
        return new UserMenu((String) VaadinSession.getCurrent().getAttribute(REGISTRANT_ID));
    }

    private SidebarMenu createTeacherMenu() {
        SidebarMenu teacherMenu = new SidebarMenu();

        NativeButton dashboardButton = new NativeButton("Задания");
        dashboardButton.addStyleName("icon-columns");
        dashboardButton.addClickListener(event -> ui.getNavigator().navigateTo(TEACHER_DASHBOARD));
        teacherMenu.addMenuButton(TeacherDashboardView.class, dashboardButton);

        NativeButton studentListButton = new NativeButton("Студенты");
        studentListButton.addStyleName("icon-users");
        teacherMenu.addMenuButton(null, studentListButton); // todo set related view class

        NativeButton createQuestionsSuiteButton = new NativeButton("Создать тестовое задание");
        createQuestionsSuiteButton.addStyleName("icon-users");
        createQuestionsSuiteButton.addClickListener(event -> ui.getNavigator().navigateTo(CREATE_QUESTIONS_SUITE));
        teacherMenu.addMenuButton(CreateQuestionsSuiteView.class, createQuestionsSuiteButton);

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
        registrationButton.addClickListener(event -> ui.getNavigator().navigateTo(USER_REGISTRATION));

        NativeButton profilesButton = new NativeButton("Пользователи");
        profilesButton.addStyleName("icon-users");
        adminMenu.addMenuButton(UserManagementView.class, profilesButton);
        profilesButton.addClickListener(event -> ui.getNavigator().navigateTo(USER_MANAGEMENT));

        NativeButton scheduleButton = new NativeButton("Расписание");
        scheduleButton.addStyleName("icon-clock");
        adminMenu.addMenuButton(ScheduleView.class, scheduleButton);
        scheduleButton.addClickListener(event -> ui.getNavigator().navigateTo(MANAGED_SCHEDULE));

        NativeButton fprintScannerButton = new NativeButton("Сканеры отпечатков");
        fprintScannerButton.addStyleName("icon-fingerprint_picture");
        adminMenu.addMenuButton(null, fprintScannerButton); // todo set related view class
        fprintScannerButton.addClickListener(event -> Notification.show("Пока не поддерживается"));

        NativeButton configButton = new NativeButton("Настройки");
        configButton.addStyleName("icon-cog-alt");
        adminMenu.addMenuButton(SettingsView.class, configButton); // todo set related view class
        configButton.addClickListener(event -> ui.getNavigator().navigateTo(SETTINGS));

        return adminMenu;
    }
}
