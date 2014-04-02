package ua.org.tees.yarosh.tais.ui.core.mvp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.org.tees.yarosh.tais.ui.core.UIContext;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unchecked")
public class ContextPresenterFactory implements PresenterFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(ContextViewFactory.class);
    private Map<Class<? extends Presenter>, Presenter> presenterPool = new HashMap<>();
    private UIContext ctx;

    public ContextPresenterFactory(UIContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public <P extends Presenter> P getPresenter(Class<P> clazz) {
        if (!presenterPool.containsKey(clazz)) {
            LOGGER.debug("Presenter [{}] will be created now", clazz.getName());
            P presenter = ctx.getBean(clazz);
            presenterPool.put(clazz, presenter);
            return presenter;
        }
        LOGGER.debug("Returning [{}] presenter", clazz.getName());
        return (P) presenterPool.get(clazz);

    }
}
