package ua.org.tees.yarosh.tais.ui.listeners.backend;

import com.google.common.eventbus.Subscribe;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.UI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.org.tees.yarosh.tais.auth.api.LoginListener;
import ua.org.tees.yarosh.tais.auth.events.LoginEvent;
import ua.org.tees.yarosh.tais.core.common.models.Registrant;
import ua.org.tees.yarosh.tais.core.user.mgmt.api.service.RegistrantService;
import ua.org.tees.yarosh.tais.homework.api.HomeworkManager;
import ua.org.tees.yarosh.tais.homework.models.ManualTaskReport;
import ua.org.tees.yarosh.tais.schedule.api.DisciplineService;
import ua.org.tees.yarosh.tais.ui.core.UIFactory;
import ua.org.tees.yarosh.tais.ui.core.api.Registrants;
import ua.org.tees.yarosh.tais.ui.views.student.UnresolvedTasksView;
import ua.org.tees.yarosh.tais.ui.views.teacher.TeacherDashboardView;

import java.util.ArrayList;
import java.util.List;

import static ua.org.tees.yarosh.tais.core.common.dto.Roles.*;

public class LoginButtonsInitializer implements LoginListener {

    public static final Logger log = LoggerFactory.getLogger(LoginButtonsInitializer.class);
    private UI ui;
    private RegistrantService registrantService;
    private HomeworkManager homeworkManager;
    private DisciplineService disciplineService;

    public LoginButtonsInitializer(UI ui,
                                   RegistrantService registrantService,
                                   HomeworkManager homeworkManager,
                                   DisciplineService disciplineService) {
        this.ui = ui;
        this.registrantService = registrantService;
        this.homeworkManager = homeworkManager;
        this.disciplineService = disciplineService;
    }

    @Override
    @Subscribe
    public void onLogin(LoginEvent event) {
        ui.access(() -> {
            log.debug("LoginEvent handler invoked");
            if (Registrants.getCurrent() == null) { //todo make thread safe
                Registrant registrant = registrantService.getRegistration(event.getUserDetails().getUsername());
                if (registrant == null) {
                    log.warn("Registrant not found");
                } else if (registrant.getRole().equals(STUDENT)) {
                    log.debug("Registrant [{}] session affected", registrant.toString());
                    setUpUnresolvedTasksButton(registrant);
                } else if (registrant.getRole().equals(TEACHER)) {
                    log.debug("Registrant [{}] session affected", registrant.toString());
                    setUpTeacherDashboardViewButton(registrant);
                } else if (registrant.getRole().equals(ADMIN)) {
                    log.debug("Registrant [{}] session affected", registrant.toString());
                    log.warn("Nothing to do");
                } else {
                    log.debug("Current registrant is null, so handler is resting");
                }
            }
        });
    }

    private void setUpTeacherDashboardViewButton(Registrant registrant) {
        List<ManualTaskReport> reports = new ArrayList<>();
        disciplineService.findDisciplinesByTeacher(registrant.getLogin())
                .forEach(d -> reports.addAll(homeworkManager.findUnratedManualTaskReports(d)));

        Button button = UIFactory.getCurrent().getSidebarManager().getSidebar()
                .getSidebarMenu().getButton(TeacherDashboardView.class);
        updateCaption(reports.size(), button);
    }

    private void setUpUnresolvedTasksButton(Registrant registrant) {
        int unresolvedManual = homeworkManager.findUnresolvedActualManualTasks(registrant).size();
        int unresolvedSuites = homeworkManager.findUnresolvedActualQuestionsSuite(registrant).size();
        int tasks = unresolvedManual + unresolvedSuites;

        Button button = UIFactory.getCurrent().getSidebarManager().getSidebar()
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
