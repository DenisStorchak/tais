package ua.org.tees.yarosh.tais.ui.views.teacher.presenters;

import com.vaadin.data.Container;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import ua.org.tees.yarosh.tais.core.common.models.Discipline;
import ua.org.tees.yarosh.tais.core.common.models.Registrant;
import ua.org.tees.yarosh.tais.core.common.models.StudentGroup;
import ua.org.tees.yarosh.tais.homework.models.ManualTask;
import ua.org.tees.yarosh.tais.homework.models.ManualTaskReport;
import ua.org.tees.yarosh.tais.ui.components.UnratedReportsDataSource;
import ua.org.tees.yarosh.tais.ui.core.mvp.AbstractPresenter;
import ua.org.tees.yarosh.tais.ui.core.mvp.SpringManagedPresenter;
import ua.org.tees.yarosh.tais.ui.core.mvp.UpdatableView;

import static ua.org.tees.yarosh.tais.ui.core.DataBinds.UriFragments.Teacher.TEACHER_DASHBOARD;
import static ua.org.tees.yarosh.tais.ui.views.teacher.api.TeacherDashboardTaisView.TeacherDashboardPresenter;

/**
 * @author Timur Yarosh
 *         Date: 22.03.14
 *         Time: 12:41
 */
@SpringManagedPresenter
public class TeacherDashboardListener extends AbstractPresenter implements TeacherDashboardPresenter {

    @Autowired
    public TeacherDashboardListener(@Qualifier(TEACHER_DASHBOARD) UpdatableView view) {
        super(view);
    }

    @Override
    public void update() {
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
        studentGroup.setId(String.valueOf(4321));

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
