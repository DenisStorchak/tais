package ua.org.tees.yarosh.tais.ui.core;

import com.vaadin.ui.Window;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import ua.org.tees.yarosh.tais.ui.core.api.Initable;
import ua.org.tees.yarosh.tais.ui.core.api.WindowFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ContextWindowFactory implements WindowFactory {

    public static final Logger log = LoggerFactory.getLogger(ContextWindowFactory.class);
    private Map<Class<? extends Window>, Window> windowPool = new ConcurrentHashMap<>();
    private WebApplicationContext ctx;

    public ContextWindowFactory(WebApplicationContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public <W extends Window> W getWindow(Class<W> windowType) {
        log.debug("Window [{}] requested", windowType);
        W window;
        if (!windowPool.containsKey(windowType)) {
            window = ctx.getBean(windowType);
            windowPool.put(windowType, window);
            log.debug("new window created");
            if (window instanceof Initable) {
                log.debug("invoke init() method");
                ((Initable) window).init();
            }
        } else {
            window = (W) windowPool.get(windowType);
        }
        return window;
    }
}
