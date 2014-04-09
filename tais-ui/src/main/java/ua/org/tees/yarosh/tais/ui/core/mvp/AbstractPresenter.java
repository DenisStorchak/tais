package ua.org.tees.yarosh.tais.ui.core.mvp;

import com.vaadin.navigator.View;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.org.tees.yarosh.tais.ui.core.api.Updatable;

/**
 * @author Timur Yarosh
 */
@SuppressWarnings("unchecked")
public abstract class AbstractPresenter implements Presenter {
    private static final Logger log = LoggerFactory.getLogger(AbstractPresenter.class);
    private Updatable view;

    public AbstractPresenter(Updatable view) {
        log.debug("AbstractPresenter instance created, view will be initialized now");
        this.view = view;
    }

    @Override
    public <V extends View> V getView(Class<V> viewClazz) {
        return (V) view;
    }

    protected void info(String message, Object... params) {
        log.info(message, params);
    }

    @Override
    public void update() {
        view.update();
    }
}
