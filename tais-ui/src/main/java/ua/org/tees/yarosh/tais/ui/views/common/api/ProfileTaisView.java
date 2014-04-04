package ua.org.tees.yarosh.tais.ui.views.common.api;

import com.vaadin.data.Container;
import com.vaadin.navigator.View;
import ua.org.tees.yarosh.tais.ui.core.mvp.Presenter;
import ua.org.tees.yarosh.tais.ui.core.mvp.UpdatableView;

public interface ProfileTaisView extends View, UpdatableView {
    interface ProfilePresenter extends Presenter {
        Container createProfileContainer();
    }
}
