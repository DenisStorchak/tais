package ua.org.tees.yarosh.tais.ui.core;

import com.vaadin.navigator.View;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
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

@Service
@Scope("prototype")
public class ViewFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(ViewFactory.class);

    private CreateSchedulePresenter createSchedulePresenter;
    private SchedulePresenter schedulePresenter;
    private UserManagementPresenter userManagementPresenter;
    private UserRegistrationPresenter userRegistrationPresenter;
    private TeacherDashboardPresenter teacherDashboardPresenter;
    private LoginPresenter loginPresenter;

    @ProvidesView(CreateScheduleView.class)
    public CreateScheduleTaisView getCreateScheduleView() {
        logViewRequest(CreateScheduleView.class);
        return createSchedulePresenter.getView(CreateScheduleTaisView.class);
    }

    @Autowired
    public void setCreateSchedulePresenter(CreateSchedulePresenter createSchedulePresenter) {
        this.createSchedulePresenter = createSchedulePresenter;
    }

    @ProvidesView(ScheduleView.class)
    public ScheduleTaisView getScheduleView() {
        logViewRequest(ScheduleView.class);
        return schedulePresenter.getView(ScheduleTaisView.class);
    }

    @Autowired
    public void setSchedulePresenter(SchedulePresenter schedulePresenter) {
        this.schedulePresenter = schedulePresenter;
    }

    @ProvidesView(UserManagementView.class)
    public UserManagementTaisView getUserManagementView() {
        logViewRequest(UserManagementView.class);
        return userManagementPresenter.getView(UserManagementTaisView.class);
    }

    @Autowired
    public void setUserManagementPresenter(UserManagementPresenter userManagementPresenter) {
        this.userManagementPresenter = userManagementPresenter;
    }

    @ProvidesView(UserRegistrationView.class)
    public UserRegistrationTaisView getUserRegistrationView() {
        logViewRequest(UserRegistrationView.class);
        return userRegistrationPresenter.getView(UserRegistrationTaisView.class);
    }

    @Autowired
    public void setUserRegistrationPresenter(UserRegistrationPresenter userRegistrationPresenter) {
        this.userRegistrationPresenter = userRegistrationPresenter;
    }

    @ProvidesView(TeacherDashboardView.class)
    public TeacherDashboardTaisView getTeacherDashboardView() {
        logViewRequest(TeacherDashboardView.class);
        return teacherDashboardPresenter.getView(TeacherDashboardTaisView.class);
    }

    @Autowired
    public void setTeacherDashboardPresenter(TeacherDashboardPresenter teacherDashboardPresenter) {
        this.teacherDashboardPresenter = teacherDashboardPresenter;
    }

    @ProvidesView(LoginView.class)
    public LoginTaisView getLoginView() {
        logViewRequest(LoginView.class);
        return loginPresenter.getView(LoginTaisView.class);
    }

    @Autowired
    public void setLoginPresenter(LoginPresenter loginPresenter) {
        this.loginPresenter = loginPresenter;
    }

    private void logViewRequest(Class<? extends View> clazz) {
        LOGGER.info("Instance of {} requested", clazz.getName());
    }
}
