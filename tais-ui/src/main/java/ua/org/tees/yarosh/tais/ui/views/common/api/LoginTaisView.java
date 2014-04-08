package ua.org.tees.yarosh.tais.ui.views.common.api;

import com.vaadin.navigator.View;
import ua.org.tees.yarosh.tais.core.common.models.Registrant;
import ua.org.tees.yarosh.tais.ui.core.api.UpdatableView;
import ua.org.tees.yarosh.tais.ui.core.mvp.Presenter;

public interface LoginTaisView extends View, UpdatableView {

    interface LoginPresenter extends Presenter {
        /**
         * @return true if login success
         */
        Registrant login(String username, String password);
    }
}
