package ua.org.tees.yarosh.tais.ui.views.teacher;

import com.vaadin.data.Container;
import ua.org.tees.yarosh.tais.ui.core.mvp.AbstractPresenter;
import ua.org.tees.yarosh.tais.ui.core.mvp.Presenter;
import ua.org.tees.yarosh.tais.ui.core.mvp.PresenterBasedView;

/**
 * @author Timur Yarosh
 *         Date: 22.03.14
 *         Time: 12:52
 */
public interface TeacherDashboardTaisView<P extends AbstractPresenter> extends PresenterBasedView<P> {
    interface TeacherDashboardPresenter extends Presenter {
        Container getUnratedManualReports();
    }
}
