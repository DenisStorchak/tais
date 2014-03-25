package ua.org.tees.yarosh.tais.ui.core;

import ua.org.tees.yarosh.tais.ui.core.mvp.ProvidesView;
import ua.org.tees.yarosh.tais.ui.views.LoginTaisView;
import ua.org.tees.yarosh.tais.ui.views.LoginView;
import ua.org.tees.yarosh.tais.ui.views.admin.*;
import ua.org.tees.yarosh.tais.ui.views.teacher.TeacherDashboardTaisView;
import ua.org.tees.yarosh.tais.ui.views.teacher.TeacherDashboardView;

public interface ViewFactory {
    @ProvidesView(CreateScheduleView.class)
    CreateScheduleTaisView getCreateScheduleView();

    @ProvidesView(ScheduleView.class)
    ScheduleTaisView getScheduleView();

    @ProvidesView(UserManagementView.class)
    UserManagementTaisView getUserManagementView();

    @ProvidesView(UserRegistrationView.class)
    UserRegistrationTaisView getUserRegistrationView();

    @ProvidesView(TeacherDashboardView.class)
    TeacherDashboardTaisView getTeacherDashboardView();

    @ProvidesView(LoginView.class)
    LoginTaisView getLoginView();
}
