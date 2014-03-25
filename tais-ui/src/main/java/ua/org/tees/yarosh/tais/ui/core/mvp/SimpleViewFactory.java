package ua.org.tees.yarosh.tais.ui.core.mvp;

import com.vaadin.navigator.View;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Timur Yarosh
 *         Date: 25.03.14
 *         Time: 21:49
 */
public class SimpleViewFactory implements ViewFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleViewFactory.class);
    private Map<Class<? extends View>, View> viewPool = new HashMap<>();
    private ApplicationContext ctx;

    public static ViewFactory createFactory(ApplicationContext ctx) {
        return new SimpleViewFactory(ctx);
    }

    private SimpleViewFactory(ApplicationContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public <V extends View> V getView(Class<V> viewClazz) {
        if (!viewPool.containsKey(viewClazz)) {
            LOGGER.info("View [{}] will be created now", viewClazz.getName());
            Class<? extends Presenter> presenterClazz = viewClazz.getAnnotation(PresenterClass.class).value();
            Presenter presenter = ctx.getBean(presenterClazz);
            V view = presenter.getView(viewClazz);
            viewPool.put(viewClazz, view);
            return view;
        }
        LOGGER.info("Returning [{}] view", viewClazz.getName());
        return (V) viewPool.get(viewClazz);
    }
}
