package ua.org.tees.yarosh.tais.ui.views.admin;

import com.vaadin.data.Container;
import ua.org.tees.yarosh.tais.ui.core.mvp.Presenter;

public interface UserManagementTaisView {

    void setRegistrantsDataSource(Container registrantsDataSource);

    interface UserManagementPresenter extends Presenter {
        Container getAllRegistrants();
    }
}
