package ua.org.tees.yarosh.tais.ui.core.mvp;

import com.vaadin.navigator.View;

/**
 * @author Timur Yarosh
 *         Date: 25.03.14
 *         Time: 22:08
 */
public interface ViewFactory {
    public <V extends View> V getView(Class<V> viewClazz);
}
