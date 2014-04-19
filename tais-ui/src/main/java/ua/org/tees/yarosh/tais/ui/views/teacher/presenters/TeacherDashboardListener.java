package ua.org.tees.yarosh.tais.ui.views.teacher.presenters;

import com.vaadin.ui.UI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import ua.org.tees.yarosh.tais.core.common.models.Discipline;
import ua.org.tees.yarosh.tais.homework.api.HomeworkManager;
import ua.org.tees.yarosh.tais.homework.models.ManualTaskReport;
import ua.org.tees.yarosh.tais.schedule.api.DisciplineService;
import ua.org.tees.yarosh.tais.ui.components.windows.CreateManualTaskWindow;
import ua.org.tees.yarosh.tais.ui.components.windows.ManualReportDetailsWindow;
import ua.org.tees.yarosh.tais.ui.core.UIFactory;
import ua.org.tees.yarosh.tais.ui.core.api.Registrants;
import ua.org.tees.yarosh.tais.ui.core.api.Updateable;
import ua.org.tees.yarosh.tais.ui.core.mvp.AbstractPresenter;
import ua.org.tees.yarosh.tais.ui.core.mvp.TaisPresenter;
import ua.org.tees.yarosh.tais.ui.views.teacher.api.TeacherDashboardTaisView;

import java.util.ArrayList;
import java.util.List;

import static ua.org.tees.yarosh.tais.ui.core.api.DataBinds.UriFragments.Teacher.CREATE_QUESTIONS_SUITE;
import static ua.org.tees.yarosh.tais.ui.core.api.DataBinds.UriFragments.Teacher.TEACHER_DASHBOARD;
import static ua.org.tees.yarosh.tais.ui.views.teacher.api.TeacherDashboardTaisView.TeacherDashboardPresenter;

/**
 * @author Timur Yarosh
 *         Date: 22.03.14
 *         Time: 12:41
 */
@TaisPresenter
public class TeacherDashboardListener extends AbstractPresenter implements TeacherDashboardPresenter {

    private HomeworkManager homeworkManager;
    private DisciplineService disciplineService;

    @Autowired
    public void setHomeworkManager(HomeworkManager homeworkManager) {
        this.homeworkManager = homeworkManager;
    }

    @Autowired
    public void setDisciplineService(DisciplineService disciplineService) {
        this.disciplineService = disciplineService;
    }

    @Autowired
    public TeacherDashboardListener(@Qualifier(TEACHER_DASHBOARD) Updateable view) {
        super(view);
    }

    @Override
    public void update() {
        onUpdate();
    }

    @Override
    public void onUpdate() {
        List<ManualTaskReport> unratedReports = new ArrayList<>();
        String login = Registrants.getCurrent().getLogin();
        List<Discipline> disciplines = disciplineService.findDisciplinesByTeacher(login);
        for (Discipline discipline : disciplines) {
            homeworkManager.findUnratedManualTaskReports(discipline).stream().forEach(unratedReports::add);
        }
        getView(TeacherDashboardTaisView.class).setUnrated(unratedReports);
    }

    @Override
    public void onDetails(ManualTaskReport report) {
        ManualReportDetailsWindow window = UIFactory.getCurrent().getWindow(ManualReportDetailsWindow.class);
        window.setReport(report);
        UI.getCurrent().addWindow(window);
    }

    @Override
    public void onCreateManualTask() {
        CreateManualTaskWindow window = UIFactory.getCurrent().getWindow(CreateManualTaskWindow.class);
        UI.getCurrent().addWindow(window);
    }

    @Override
    public void onCreateQuestionsSuite() {
        UI.getCurrent().getNavigator().navigateTo(CREATE_QUESTIONS_SUITE);
    }
}
