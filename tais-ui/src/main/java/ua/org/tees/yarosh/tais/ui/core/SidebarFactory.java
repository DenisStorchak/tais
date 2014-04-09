package ua.org.tees.yarosh.tais.ui.core;

import com.vaadin.server.VaadinSession;
import com.vaadin.ui.NativeButton;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.org.tees.yarosh.tais.core.common.dto.Roles;
import ua.org.tees.yarosh.tais.ui.components.layouts.Sidebar;
import ua.org.tees.yarosh.tais.ui.components.layouts.SidebarMenu;
import ua.org.tees.yarosh.tais.ui.components.layouts.UserMenu;
import ua.org.tees.yarosh.tais.ui.views.admin.Schedule;
import ua.org.tees.yarosh.tais.ui.views.admin.Settings;
import ua.org.tees.yarosh.tais.ui.views.admin.UserManagement;
import ua.org.tees.yarosh.tais.ui.views.admin.UserRegistration;
import ua.org.tees.yarosh.tais.ui.views.teacher.CreateQuestionsSuite;
import ua.org.tees.yarosh.tais.ui.views.teacher.EnabledQuestionsSuites;
import ua.org.tees.yarosh.tais.ui.views.teacher.StudentsView;
import ua.org.tees.yarosh.tais.ui.views.teacher.TeacherDashboard;

import static ua.org.tees.yarosh.tais.ui.core.DataBinds.SessionKeys.REGISTRANT_ID;
import static ua.org.tees.yarosh.tais.ui.core.DataBinds.UriFragments.Admin.*;
import static ua.org.tees.yarosh.tais.ui.core.DataBinds.UriFragments.Teacher.*;

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
        teacherMenu.addMenuButton(TeacherDashboard.class, dashboardButton);

        NativeButton createQuestionsSuiteButton = new NativeButton("Создать тест");
        createQuestionsSuiteButton.addStyleName("icon-users");
        createQuestionsSuiteButton.addClickListener(event -> ui.getNavigator().navigateTo(CREATE_QUESTIONS_SUITE));
        teacherMenu.addMenuButton(CreateQuestionsSuite.class, createQuestionsSuiteButton);

        NativeButton showAllQuestionsSuites = new NativeButton("Тесты");
        showAllQuestionsSuites.addStyleName("icon-users");
        showAllQuestionsSuites.addClickListener(event -> ui.getNavigator().navigateTo(ENABLED_QUESTIONS));
        teacherMenu.addMenuButton(EnabledQuestionsSuites.class, showAllQuestionsSuites);

        NativeButton showAllStudents = new NativeButton("Студенты");
        showAllStudents.addStyleName("icon-users");
        showAllStudents.addClickListener(event -> ui.getNavigator().navigateTo(STUDENTS));
        teacherMenu.addMenuButton(StudentsView.class, showAllStudents);

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
        adminMenu.addMenuButton(UserRegistration.class, registrationButton);
        registrationButton.addClickListener(event -> ui.getNavigator().navigateTo(USER_REGISTRATION));

        NativeButton profilesButton = new NativeButton("Пользователи");
        profilesButton.addStyleName("icon-users");
        adminMenu.addMenuButton(UserManagement.class, profilesButton);
        profilesButton.addClickListener(event -> ui.getNavigator().navigateTo(USER_MANAGEMENT));

        NativeButton scheduleButton = new NativeButton("Расписание");
        scheduleButton.addStyleName("icon-clock");
        adminMenu.addMenuButton(Schedule.class, scheduleButton);
        scheduleButton.addClickListener(event -> ui.getNavigator().navigateTo(MANAGED_SCHEDULE));

        NativeButton fprintScannerButton = new NativeButton("Сканеры отпечатков");
        fprintScannerButton.addStyleName("icon-fingerprint_picture");
        adminMenu.addMenuButton(null, fprintScannerButton); // todo set related view class
        fprintScannerButton.addClickListener(event -> Notification.show("Пока не поддерживается"));

        NativeButton configButton = new NativeButton("Настройки");
        configButton.addStyleName("icon-cog-alt");
        adminMenu.addMenuButton(Settings.class, configButton);
        configButton.addClickListener(event -> ui.getNavigator().navigateTo(SETTINGS));

        return adminMenu;
    }
}
