package ua.org.tees.yarosh.tais.ui.views.teacher.api;

import com.vaadin.data.Container;
import com.vaadin.navigator.View;
import ua.org.tees.yarosh.tais.ui.core.mvp.Presenter;
import ua.org.tees.yarosh.tais.ui.core.mvp.UpdatableView;

/**
 * @author Timur Yarosh
 *         Date: 22.03.14
 *         Time: 12:52
 */
public interface TeacherDashboardTaisView extends View, UpdatableView {
    interface TeacherDashboardPresenter extends Presenter {
        Container getUnratedManualReports();
    }
}
