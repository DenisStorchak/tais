package ua.org.tees.yarosh.tais.ui.views.teacher;

import com.vaadin.data.Container;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import ua.org.tees.yarosh.tais.core.common.models.Discipline;
import ua.org.tees.yarosh.tais.core.common.models.Registrant;
import ua.org.tees.yarosh.tais.core.common.models.StudentGroup;
import ua.org.tees.yarosh.tais.homework.models.ManualTask;
import ua.org.tees.yarosh.tais.homework.models.ManualTaskReport;
import ua.org.tees.yarosh.tais.ui.core.HelpManager;
import ua.org.tees.yarosh.tais.ui.core.components.UnratedReportsDataSource;
import ua.org.tees.yarosh.tais.ui.core.mvp.AbstractPresenter;
import ua.org.tees.yarosh.tais.ui.core.mvp.TaisView;

import static ua.org.tees.yarosh.tais.ui.core.UriFragments.Teacher.TEACHER_DASHBOARD;

/**
 * @author Timur Yarosh
 *         Date: 22.03.14
 *         Time: 12:41
 */
@Service
@Scope("prototype")
public class TeacherDashboardListener extends AbstractPresenter implements TeacherDashboardTaisView.TeacherDashboardPresenter {

    @Autowired
    public TeacherDashboardListener(@Qualifier(TEACHER_DASHBOARD) TaisView view, HelpManager helpManager) {
        super(view, helpManager);
    }

    @Override
    protected void initView(TaisView view) {
        getHelpManager().closeAll();
    }

    @Override
    public Container getUnratedManualReports() {
        UnratedReportsDataSource unratedReports = new UnratedReportsDataSource();
        for (long i = 0; i < 100; i++) {
            unratedReports.addReport(createSampleReport(i));
        }
        return unratedReports;
    }

    private ManualTaskReport createSampleReport(long i) {
        StudentGroup studentGroup = new StudentGroup();
        studentGroup.setId(4321);

        Registrant owner = new Registrant();
        owner.setName("Тимур");
        owner.setSurname("Ярош");
        owner.setGroup(studentGroup);

        Discipline discipline = new Discipline();
        discipline.setName("Охрана труда");

        ManualTask manualTask = new ManualTask();
        manualTask.setDiscipline(discipline);

        manualTask.setStudentGroup(studentGroup);

        ManualTaskReport taskReport = new ManualTaskReport();
        taskReport.setId(i);
        taskReport.setOwner(owner);
        taskReport.setTask(manualTask);
        return taskReport;
    }
}
