package ua.org.tees.yarosh.tais.ui.core.mvp;

import com.vaadin.navigator.View;

public interface Presenter {
    <V extends View> V getView(Class<V> viewClazz);

    void update();
}
