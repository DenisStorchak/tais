package ua.org.tees.yarosh.tais.ui.views.common.api;

import com.vaadin.navigator.View;
import ua.org.tees.yarosh.tais.core.common.models.Registrant;
import ua.org.tees.yarosh.tais.ui.core.api.Updatable;
import ua.org.tees.yarosh.tais.ui.core.mvp.Presenter;

public interface LoginTais extends View, Updatable {

    interface LoginPresenter extends Presenter {
        /**
         * @return true if login success
         */
        Registrant login(String username, String password);
    }
}
