package ua.org.tees.yarosh.tais.ui.core.mvp;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import ua.org.tees.yarosh.tais.ui.core.HelpManager;
import ua.org.tees.yarosh.tais.ui.core.components.PresenterBasedView;

public interface Presenter {
    HelpManager getHelpManager();

    PresenterBasedView getView();

    <V extends View> V getView(Class<V> viewClazz);

    void navigateBack(Navigator navigator);

    void initView();
}
