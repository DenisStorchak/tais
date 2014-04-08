package ua.org.tees.yarosh.tais.ui.core.mvp;

import com.vaadin.navigator.View;
import ua.org.tees.yarosh.tais.ui.core.api.Initable;

public interface Presenter extends Initable {
    <V extends View> V getView(Class<V> viewClazz);

    void update();
}
