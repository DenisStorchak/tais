package ua.org.tees.yarosh.tais.ui.core.mvp;

import com.vaadin.navigator.View;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Timur Yarosh
 */
@SuppressWarnings("unchecked")
public abstract class AbstractPresenter implements Presenter {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractPresenter.class);
    private UpdatableView view;

    public AbstractPresenter(UpdatableView view) {
        LOGGER.debug("AbstractPresenter instance created, view will be initialized now");
        this.view = view;
    }

    @Override
    public <V extends View> V getView(Class<V> viewClazz) {
        return (V) view;
    }

    protected void info(String message, Object... params) {
        LOGGER.info(message, params);
    }

    @Override
    public void update() {
        view.update();
    }
}
