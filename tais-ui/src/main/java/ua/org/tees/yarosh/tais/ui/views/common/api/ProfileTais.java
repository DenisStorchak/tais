package ua.org.tees.yarosh.tais.ui.views.common.api;

import com.vaadin.data.Container;
import com.vaadin.navigator.View;
import ua.org.tees.yarosh.tais.ui.core.api.Updatable;
import ua.org.tees.yarosh.tais.ui.core.mvp.Presenter;

public interface ProfileTais extends View, Updatable {
    interface ProfilePresenter extends Presenter {
        Container createProfileContainer();
    }
}
