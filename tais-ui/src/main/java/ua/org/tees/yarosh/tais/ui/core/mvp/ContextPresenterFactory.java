package ua.org.tees.yarosh.tais.ui.core.mvp;

import com.vaadin.navigator.View;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.org.tees.yarosh.tais.ui.core.UIContext;

import java.util.HashMap;
import java.util.Map;

import static java.lang.String.format;

@SuppressWarnings("unchecked")
public class ContextPresenterFactory implements PresenterFactory {

    private static final Logger log = LoggerFactory.getLogger(ContextViewFactory.class);
    private static final String PRESENTER_NOT_RELATED = "Presenter [%s] isn't relative with view [%s]";
    private Map<Class<? extends Presenter>, Presenter> presenterPool = new HashMap<>();
    private UIContext ctx;

    public ContextPresenterFactory(UIContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public <P extends Presenter> P getPresenter(Class<P> clazz) {
        return localGetPresenter(clazz);
    }

    @Override
    public <P extends Presenter> P getRelativePresenter(View view, Class<P> clazz) {
        return getRelativePresenter(view.getClass(), clazz);
    }

    @Override
    public <P extends Presenter> P getRelativePresenter(Class<? extends View> viewClazz, Class<P> presenterClazz) {
        PresentedBy presentedBy = viewClazz.getAnnotation(PresentedBy.class);
        if (presentedBy.value().equals(presenterClazz)) {
            return localGetPresenter(presenterClazz);
        }
        throw new IllegalArgumentException(format(PRESENTER_NOT_RELATED, presenterClazz.getName(), viewClazz.getName()));
    }

    private <P extends Presenter> P localGetPresenter(Class<P> clazz) {
        if (!clazz.isInterface()) {
            throw new IllegalArgumentException("Interface required");
        }
        if (!presenterPool.containsKey(clazz)) {
            log.debug("Presenter [{}] will be created now", clazz.getName());
            P presenter = ctx.getBean(clazz);
            presenterPool.put(clazz, presenter);
            return presenter;
        }
        log.debug("Returning [{}] presenter", clazz.getName());
        return (P) presenterPool.get(clazz);
    }
}
