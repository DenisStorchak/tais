package ua.org.tees.yarosh.tais.ui.core.mvp;

import com.vaadin.navigator.View;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import ua.org.tees.yarosh.tais.ui.core.api.Initable;
import ua.org.tees.yarosh.tais.ui.core.api.PresenterUnnecessary;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Timur Yarosh
 *         Date: 25.03.14
 *         Time: 21:49
 */
@SuppressWarnings("unchecked")
public class ContextViewFactory implements ViewFactory {

    private static final Logger log = LoggerFactory.getLogger(ContextViewFactory.class);
    private Map<Class<? extends View>, View> viewPool = new HashMap<>();
    private PresenterFactory presenterFactory;
    private WebApplicationContext ctx;

    public ContextViewFactory(PresenterFactory presenterFactory, WebApplicationContext ctx) {
        this.presenterFactory = presenterFactory;
        this.ctx = ctx;
    }

    @Override
    public <V extends View> V getView(Class<V> viewClazz) {
        if (viewClazz.getAnnotation(TaisView.class) == null) {
            log.error("View should be annotated with [{}]", TaisView.class.getName());
        }
        if (!viewPool.containsKey(viewClazz)) {
            log.debug("View [{}] will be created now", viewClazz.getName());
            V view;
            if (!viewClazz.isAnnotationPresent(PresenterUnnecessary.class)) {
                Class<? extends Presenter> presenterClazz = viewClazz.getAnnotation(PresentedBy.class).value();
                Presenter presenter = presenterFactory.getRelativePresenter(viewClazz, presenterClazz);
                view = presenter.getView(viewClazz);
            } else {
                view = ctx.getBean(viewClazz);
            }
            viewPool.put(viewClazz, view);

            if (view instanceof Initable) {
                ((Initable) view).init();
            }

            return view;
        }
        log.debug("Returning [{}] view", viewClazz.getName());
        return (V) viewPool.get(viewClazz);
    }
}
