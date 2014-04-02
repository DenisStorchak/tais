package ua.org.tees.yarosh.tais.ui.core.mvp;

import com.vaadin.ui.Window;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.org.tees.yarosh.tais.ui.core.UIContext;

public class ContextWindowFactory implements WindowFactory {

    public static final Logger LOGGER = LoggerFactory.getLogger(ContextWindowFactory.class);
    private UIContext ctx;

    public ContextWindowFactory(UIContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public <W extends Window> W getWindow(Class<W> windowType) {
        LOGGER.debug("Window [{}] will be created", windowType);
        return ctx.getBean(windowType);
    }
}
