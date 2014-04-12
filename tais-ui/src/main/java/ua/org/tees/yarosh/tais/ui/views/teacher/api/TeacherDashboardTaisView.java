package ua.org.tees.yarosh.tais.ui.views.teacher.api;

import com.vaadin.navigator.View;
import ua.org.tees.yarosh.tais.homework.models.ManualTaskReport;
import ua.org.tees.yarosh.tais.ui.core.api.Updateable;
import ua.org.tees.yarosh.tais.ui.core.mvp.Presenter;

import java.util.List;

/**
 * @author Timur Yarosh
 *         Date: 22.03.14
 *         Time: 12:52
 */
public interface TeacherDashboardTaisView extends View, Updateable {

    void setUnrated(List<ManualTaskReport> unratedReports);

    interface TeacherDashboardPresenter extends Presenter {
        void onUpdate();

        void onDetails(ManualTaskReport report);

        void onCreateManualTask();

        void onCreateQuestionsSuite();
    }
}
