package ua.org.tees.yarosh.tais.ui.core.mvp;

import com.vaadin.navigator.View;

public interface PresenterFactory {
    <P extends Presenter> P getPresenter(Class<P> clazz);

    <P extends Presenter> P getRelativePresenter(View view, Class<P> clazz);
}
