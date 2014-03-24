package ua.org.tees.yarosh.tais.ui.views.admin;

import com.vaadin.data.Container;
import ua.org.tees.yarosh.tais.ui.core.components.PresenterBasedView;

public interface UserManagementTaisView extends PresenterBasedView<UserManagementListener> {

    void setRegistrantsDataSource(Container registrantsDataSource);

    interface UserListPresenter {
        Container getAllRegistrants();
    }
}
