package ua.org.tees.yarosh.tais.ui.core.mvp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import ua.org.tees.yarosh.tais.ui.core.UIContext;

import static ua.org.tees.yarosh.tais.ui.core.bindings.Qualifiers.WINDOW_FACTORY;

@Service
@Scope("prototype")
@Qualifier(WINDOW_FACTORY)
public class SpringManagedWindowFactory implements WindowFactory {

    public static final Logger LOGGER = LoggerFactory.getLogger(SpringManagedWindowFactory.class);
    private UIContext ctx;

    @Autowired
    public SpringManagedWindowFactory(UIContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public <T> T getWindow(Class<T> windowType) {
        LOGGER.debug("Window [{}] will be created", windowType);
        return ctx.getBean(windowType);
    }
}
