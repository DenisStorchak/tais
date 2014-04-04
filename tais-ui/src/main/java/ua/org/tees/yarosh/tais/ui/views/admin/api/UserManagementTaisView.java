package ua.org.tees.yarosh.tais.ui.views.admin.api;

import com.vaadin.data.Container;
import com.vaadin.navigator.View;
import ua.org.tees.yarosh.tais.ui.core.mvp.Presenter;
import ua.org.tees.yarosh.tais.ui.core.mvp.UpdatableView;

public interface UserManagementTaisView extends View, UpdatableView {

    interface UserManagementPresenter extends Presenter {
        Container getAllRegistrants();
    }
}
