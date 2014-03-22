package ua.org.tees.yarosh.tais.ui.roles.teacher;

import com.vaadin.data.Container;
import ua.org.tees.yarosh.tais.ui.core.mvp.TaisView;

/**
 * @author Timur Yarosh
 *         Date: 22.03.14
 *         Time: 12:52
 */
public interface TeacherDashboardTaisView extends TaisView {
    interface TeacherDashboardPresenter {
        Container getUnratedManualReports();
    }
}
