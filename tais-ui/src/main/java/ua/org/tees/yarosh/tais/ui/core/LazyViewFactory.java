package ua.org.tees.yarosh.tais.ui.core;

import com.vaadin.navigator.View;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import ua.org.tees.yarosh.tais.ui.core.mvp.Presenter;
import ua.org.tees.yarosh.tais.ui.core.mvp.ProvidesView;
import ua.org.tees.yarosh.tais.ui.views.LoginTaisView;
import ua.org.tees.yarosh.tais.ui.views.LoginView;
import ua.org.tees.yarosh.tais.ui.views.admin.*;
import ua.org.tees.yarosh.tais.ui.views.teacher.TeacherDashboardTaisView;
import ua.org.tees.yarosh.tais.ui.views.teacher.TeacherDashboardView;

import static ua.org.tees.yarosh.tais.ui.views.LoginTaisView.LoginPresenter;
import static ua.org.tees.yarosh.tais.ui.views.admin.CreateScheduleTaisView.CreateSchedulePresenter;
import static ua.org.tees.yarosh.tais.ui.views.admin.ScheduleTaisView.SchedulePresenter;
import static ua.org.tees.yarosh.tais.ui.views.admin.UserManagementTaisView.UserManagementPresenter;
import static ua.org.tees.yarosh.tais.ui.views.admin.UserRegistrationTaisView.UserRegistrationPresenter;
import static ua.org.tees.yarosh.tais.ui.views.teacher.TeacherDashboardTaisView.TeacherDashboardPresenter;

public class LazyViewFactory implements ViewFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(LazyViewFactory.class);

    private CreateSchedulePresenter createSchedulePresenter;
    private SchedulePresenter schedulePresenter;
    private UserManagementPresenter userManagementPresenter;
    private UserRegistrationPresenter userRegistrationPresenter;
    private TeacherDashboardPresenter teacherDashboardPresenter;
    private LoginPresenter loginPresenter;

    private WebApplicationContext ctx;

    public LazyViewFactory(WebApplicationContext ctx) {
        this.ctx = ctx;
    }

    @Override
    @ProvidesView(CreateScheduleView.class)
    public CreateScheduleTaisView getCreateScheduleView() {
        logViewRequest(CreateScheduleView.class);
        if (createSchedulePresenter == null) {
            logPresenterCreation(CreateSchedulePresenter.class);
            createSchedulePresenter = ctx.getBean(CreateSchedulePresenter.class);
        }
        return createSchedulePresenter.getView(CreateScheduleTaisView.class);
    }

    @Override
    @ProvidesView(ScheduleView.class)
    public ScheduleTaisView getScheduleView() {
        logViewRequest(ScheduleView.class);
        if (schedulePresenter == null) {
            logPresenterCreation(SchedulePresenter.class);
            schedulePresenter = ctx.getBean(SchedulePresenter.class);
        }
        return schedulePresenter.getView(ScheduleTaisView.class);
    }

    @Override
    @ProvidesView(UserManagementView.class)
    public UserManagementTaisView getUserManagementView() {
        logViewRequest(UserManagementView.class);
        if (userManagementPresenter == null) {
            logPresenterCreation(UserManagementPresenter.class);
            userManagementPresenter = ctx.getBean(UserManagementPresenter.class);
        }
        return userManagementPresenter.getView(UserManagementTaisView.class);
    }

    @Override
    @ProvidesView(UserRegistrationView.class)
    public UserRegistrationTaisView getUserRegistrationView() {
        logViewRequest(UserRegistrationView.class);
        if (userRegistrationPresenter == null) {
            logPresenterCreation(UserRegistrationPresenter.class);
            userRegistrationPresenter = ctx.getBean(UserRegistrationPresenter.class);
        }
        return userRegistrationPresenter.getView(UserRegistrationTaisView.class);
    }

    @Override
    @ProvidesView(TeacherDashboardView.class)
    public TeacherDashboardTaisView getTeacherDashboardView() {
        logViewRequest(TeacherDashboardView.class);
        if (teacherDashboardPresenter == null) {
            logPresenterCreation(TeacherDashboardPresenter.class);
            teacherDashboardPresenter = ctx.getBean(TeacherDashboardPresenter.class);
        }
        return teacherDashboardPresenter.getView(TeacherDashboardTaisView.class);
    }

    @Override
    @ProvidesView(LoginView.class)
    public LoginTaisView getLoginView() {
        logViewRequest(LoginView.class);
        if (loginPresenter == null) {
            logPresenterCreation(LoginPresenter.class);
            loginPresenter = ctx.getBean(LoginPresenter.class);
        }
        return loginPresenter.getView(LoginTaisView.class);
    }

    private void logViewRequest(Class<? extends View> clazz) {
        LOGGER.info("Instance of [{}] requested", clazz.getName());
    }

    private void logPresenterCreation(Class<? extends Presenter> clazz) {
        LOGGER.info("Presenter is null, I'll create presenter [{}]", clazz.getName());
    }
}
