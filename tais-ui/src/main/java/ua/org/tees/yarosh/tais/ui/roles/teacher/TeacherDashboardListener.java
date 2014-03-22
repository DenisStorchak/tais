package ua.org.tees.yarosh.tais.ui.roles.teacher;

import com.vaadin.data.Container;
import ua.org.tees.yarosh.tais.core.common.models.Discipline;
import ua.org.tees.yarosh.tais.core.common.models.Registrant;
import ua.org.tees.yarosh.tais.core.common.models.StudentGroup;
import ua.org.tees.yarosh.tais.homework.models.ManualTask;
import ua.org.tees.yarosh.tais.homework.models.ManualTaskReport;
import ua.org.tees.yarosh.tais.ui.core.components.UnratedReportsDataSource;
import ua.org.tees.yarosh.tais.ui.core.mvp.AbstractPresenter;
import ua.org.tees.yarosh.tais.ui.core.mvp.TaisView;

/**
 * @author Timur Yarosh
 *         Date: 22.03.14
 *         Time: 12:41
 */
public class TeacherDashboardListener extends AbstractPresenter implements TeacherDashboardTaisView.TeacherDashboardPresenter {

    public TeacherDashboardListener(TaisView view) {
        super(view);
    }

    @Override
    protected void initView() {
        getView().addPresenter(this);
    }

    @Override
    public Container getUnratedManualReports() {
        UnratedReportsDataSource unratedReports = new UnratedReportsDataSource();
        unratedReports.addReport(createSampleReport());
        return unratedReports;
    }

    private ManualTaskReport createSampleReport() {
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
        taskReport.setId(0L);
        taskReport.setOwner(owner);
        taskReport.setTask(manualTask);
        return taskReport;
    }
}
