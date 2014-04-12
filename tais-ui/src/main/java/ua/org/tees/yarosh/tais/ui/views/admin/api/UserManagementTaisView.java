package ua.org.tees.yarosh.tais.ui.views.admin.api;

import com.vaadin.data.Container;
import com.vaadin.navigator.View;
import ua.org.tees.yarosh.tais.ui.core.api.Updateable;
import ua.org.tees.yarosh.tais.ui.core.mvp.Presenter;

public interface UserManagementTaisView extends View, Updateable {

    void setRegistrantsDataSource(Container registrantsDataSource);

    interface UserManagementPresenter extends Presenter {
        Container getAllRegistrants();

        void onCreateRegistration();

        void onCreateGroup();

        void onCreateClassroom();

        void onCreateDiscipline();
    }
}
