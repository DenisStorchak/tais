package ua.org.tees.yarosh.tais.ui.core.mvp;

import com.vaadin.navigator.View;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.org.tees.yarosh.tais.ui.core.api.Updateable;

/**
 * @author Timur Yarosh
 */
@SuppressWarnings("unchecked")
public abstract class AbstractPresenter implements Presenter {
    public static final Logger log = LoggerFactory.getLogger(AbstractPresenter.class);
    private Updateable view;

    public AbstractPresenter(Updateable view) {
        log.debug("AbstractPresenter instance created, view will be initialized now");
        this.view = view;
    }

    @Override
    public <V extends View> V getView(Class<V> viewClazz) {
        return (V) view;
    }

    @Override
    public void update() {
        view.update();
    }
}
