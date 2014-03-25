package ua.org.tees.yarosh.tais.ui.views.admin;

import com.vaadin.data.Container;
import ua.org.tees.yarosh.tais.ui.core.mvp.Presenter;
import ua.org.tees.yarosh.tais.ui.core.mvp.PresenterBasedView;

public interface UserManagementTaisView extends PresenterBasedView<UserManagementTaisView.UserManagementPresenter> {

    void setRegistrantsDataSource(Container registrantsDataSource);

    interface UserManagementPresenter extends Presenter {
        Container getAllRegistrants();
    }
}
