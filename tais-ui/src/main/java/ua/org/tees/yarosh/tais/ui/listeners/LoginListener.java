package ua.org.tees.yarosh.tais.ui.listeners;

import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.Subscribe;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.UI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import ua.org.tees.yarosh.tais.auth.events.LoginEvent;
import ua.org.tees.yarosh.tais.core.common.models.Registrant;
import ua.org.tees.yarosh.tais.homework.api.HomeworkManager;
import ua.org.tees.yarosh.tais.homework.models.ManualTaskReport;
import ua.org.tees.yarosh.tais.schedule.api.DisciplineService;
import ua.org.tees.yarosh.tais.ui.core.UIFactoryAccessor;
import ua.org.tees.yarosh.tais.ui.views.student.UnresolvedTasksView;
import ua.org.tees.yarosh.tais.ui.views.teacher.TeacherDashboardView;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.web.context.support.WebApplicationContextUtils.getRequiredWebApplicationContext;
import static ua.org.tees.yarosh.tais.core.common.dto.Roles.*;

public class LoginListener implements ua.org.tees.yarosh.tais.ui.core.api.LoginListener {

    public static final Logger log = LoggerFactory.getLogger(LoginListener.class);
    private UI ui;

    public LoginListener(UI ui) {
        this.ui = ui;
    }

    @Override
    @Subscribe
    @AllowConcurrentEvents
    public void onLogin(LoginEvent event) {
        ui.access(() -> {
            log.debug("LoginEvent handler invoked");
            Registrant registrant = event.getUserDetails();
            if (registrant.getRole().equals(STUDENT)) {
                log.debug("Registrant [{}] session affected", registrant.toString());
                setUpUnresolvedTasksButton(event);
            } else if (registrant.getRole().equals(TEACHER)) {
                log.debug("Registrant [{}] session affected", registrant.toString());
                setUpTeacherDashboardViewButton(event);
            } else if (registrant.getRole().equals(ADMIN)) {
                log.debug("Registrant [{}] session affected", registrant.toString());
                log.warn("Nothing to do");
            } else {
                log.debug("Current registrant is null, so handler is resting");
            }
        });
    }

    private void setUpTeacherDashboardViewButton(LoginEvent event) {
        WebApplicationContext ctx = getRequiredWebApplicationContext(VaadinServlet.getCurrent().getServletContext());
        HomeworkManager homeworkManager = ctx.getBean(HomeworkManager.class);
        DisciplineService disciplineService = ctx.getBean(DisciplineService.class);

        List<ManualTaskReport> reports = new ArrayList<>();
        disciplineService.findDisciplinesByTeacher(event.getUserDetails().getLogin())
                .forEach(d -> reports.addAll(homeworkManager.findUnratedManualTaskReports(d)));

        Button button = UIFactoryAccessor.getCurrent().getSidebarManager().getSidebar()
                .getSidebarMenu().getButton(TeacherDashboardView.class);
        updateCaption(reports.size(), button);
    }

    private void setUpUnresolvedTasksButton(LoginEvent event) {
        WebApplicationContext ctx = getRequiredWebApplicationContext(VaadinServlet.getCurrent().getServletContext());
        HomeworkManager homeworkManager = ctx.getBean(HomeworkManager.class);

        int unresolvedManual = homeworkManager.findUnresolvedActualManualTasks(event.getUserDetails()).size();
        int unresolvedSuites = homeworkManager.findUnresolvedActualQuestionsSuite(event.getUserDetails()).size();
        int tasks = unresolvedManual + unresolvedSuites;

        Button button = UIFactoryAccessor.getCurrent().getSidebarManager().getSidebar()
                .getSidebarMenu().getButton(UnresolvedTasksView.class);
        updateCaption(tasks, button);
    }

    private void updateCaption(int number, Component component) {
        if (component != null) {
            log.debug("Old component caption is [{}]", component.getCaption());
            component.setCaption(component.getCaption().replaceAll("\\d+", String.valueOf(number)));
            log.debug("New component caption is [{}]", component.getCaption());
        } else {
            log.warn("component is null");
        }
    }
}
