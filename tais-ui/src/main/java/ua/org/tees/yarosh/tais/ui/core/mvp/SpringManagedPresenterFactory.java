package ua.org.tees.yarosh.tais.ui.core.mvp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import ua.org.tees.yarosh.tais.ui.core.UIContext;

import java.util.HashMap;
import java.util.Map;

import static ua.org.tees.yarosh.tais.ui.core.bindings.Qualifiers.PRESENTER_FACTORY;

@Service
@Scope("prototype")
@Qualifier(PRESENTER_FACTORY)
@SuppressWarnings("unchecked")
public class SpringManagedPresenterFactory implements PresenterFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpringManagedViewFactory.class);
    private Map<Class<? extends Presenter>, Presenter> presenterPool = new HashMap<>();
    private UIContext ctx;

    @Autowired
    public SpringManagedPresenterFactory(UIContext ctx) {
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
