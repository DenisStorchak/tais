package ua.org.tees.yarosh.tais.ui.core;

import com.vaadin.ui.Window;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.org.tees.yarosh.tais.ui.core.api.Initable;
import ua.org.tees.yarosh.tais.ui.core.api.UIContext;
import ua.org.tees.yarosh.tais.ui.core.api.WindowFactory;

public class ContextWindowFactory implements WindowFactory {

    public static final Logger log = LoggerFactory.getLogger(ContextWindowFactory.class);
    private UIContext ctx;

    public ContextWindowFactory(UIContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public <W extends Window> W getWindow(Class<W> windowType) {
        log.debug("Window [{}] will be created", windowType);
        W window = ctx.getBean(windowType);
        ((Initable) window).init();
        return window;
    }
}
