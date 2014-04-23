package ua.org.tees.yarosh.tais.ui.views.common.api;

import com.vaadin.data.Container;
import com.vaadin.navigator.View;
import ua.org.tees.yarosh.tais.ui.core.api.Updateable;
import ua.org.tees.yarosh.tais.ui.core.mvp.Presenter;

public interface ProfileTaisView extends View, Updateable {
    interface ProfilePresenter extends Presenter {
        Container createProfileContainer();
    }
}
