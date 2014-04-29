package ua.org.tees.yarosh.tais.ui.core;

import com.vaadin.navigator.View;
import com.vaadin.server.VaadinServlet;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Window;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import ua.org.tees.yarosh.tais.ui.components.HelpManager;
import ua.org.tees.yarosh.tais.ui.core.api.ComponentFactory;
import ua.org.tees.yarosh.tais.ui.core.api.HelpManagerFactory;
import ua.org.tees.yarosh.tais.ui.core.api.SidebarManagerFactory;
import ua.org.tees.yarosh.tais.ui.core.api.WindowFactory;
import ua.org.tees.yarosh.tais.ui.core.mvp.*;
import ua.org.tees.yarosh.tais.ui.listeners.SidebarManager;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static ua.org.tees.yarosh.tais.ui.core.api.DataBinds.SessionKeys.COMPONENT_FACTORY;

public class UIFactory implements ComponentFactory, Serializable {

    public static final Logger log = LoggerFactory.getLogger(UIFactory.class);
    private static final Map<String, ComponentFactory> INSTANCES = new ConcurrentHashMap<>();

    private ViewFactory viewFactory;
    private WindowFactory windowFactory;
    private PresenterFactory presenterFactory;
    private HelpManagerFactory helpManagerFactory;
    private SidebarManagerFactory sidebarManagerFactory;

    private UIFactory(PresenterFactory presenterFactory,
                      ViewFactory viewFactory,
                      WindowFactory windowFactory,
                      HelpManagerFactory helpManagerFactory,
                      SidebarManagerFactory sidebarManagerFactory) {
        this.viewFactory = viewFactory;
        this.windowFactory = windowFactory;
        this.presenterFactory = presenterFactory;
        this.helpManagerFactory = helpManagerFactory;
        this.sidebarManagerFactory = sidebarManagerFactory;
    }

    @Override
    public <V extends View> V getView(Class<V> viewClazz) {
        return viewFactory.getView(viewClazz);
    }

    @Override
    public <W extends Window> W getWindow(Class<W> windowType) {
        log.debug("Window [{}] requested", windowType);
        return windowFactory.getWindow(windowType);
    }

    @Override
    public <P extends Presenter> P getPresenter(Class<P> clazz) {
        return presenterFactory.getPresenter(clazz);
    }

    @Override
    public <P extends Presenter> P getRelativePresenter(View view, Class<P> clazz) {
        return presenterFactory.getRelativePresenter(view, clazz);
    }

    @Override
    public <P extends Presenter> P getRelativePresenter(Class<? extends View> viewClazz, Class<P> presenterClazz) {
        return presenterFactory.getRelativePresenter(viewClazz, presenterClazz);
    }

    @Override
    public HelpManager getHelpManager() {
        return helpManagerFactory.getHelpManager();
    }

    @Override
    public SidebarManager getSidebarManager() {
        return sidebarManagerFactory.getSidebarManager();
    }

    /**
     * Get factory from current vaadin session if presented or create and save it
     *
     * @return factory
     */
    public static ComponentFactory getCurrent() {
        return getCurrent(VaadinSession.getCurrent());
    }

    public static ComponentFactory getCurrent(VaadinSession session) {
        ComponentFactory instance = (ComponentFactory) session.getAttribute(COMPONENT_FACTORY);
        if (instance == null) {
            log.debug("instance is null, new instance will be created now");
            instance = createAndSaveFactory();
        }
        log.debug("instance returning");
        return instance;
    }

    public static ComponentFactory getCurrent(VaadinSession session, VaadinServlet servlet) {
        if (!INSTANCES.containsKey(session.getSession().getId())) {
            log.debug("instance is null, new instance will be created now");
            return createAndSaveFactory(servlet, session);
        }

        log.debug("instance returning");
        return INSTANCES.get(session.getSession().getId());
    }

    /**
     * Create factory and save it to current vaadin session
     */
    private static ComponentFactory createAndSaveFactory() {
        return createAndSaveFactory(VaadinServlet.getCurrent(), VaadinSession.getCurrent());
    }

    /**
     * Create factory and save it to vaadin session
     */
    private static ComponentFactory createAndSaveFactory(VaadinServlet vaadinServlet, VaadinSession vaadinSession) {
        log.debug("creating new factory instance");
        WebApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(
                vaadinServlet.getServletContext());
        ContextPresenterFactory presenterFactory = new ContextPresenterFactory(ctx);
        ContextViewFactory viewFactory = new ContextViewFactory(presenterFactory, ctx);
        ContextWindowFactory windowFactory = new ContextWindowFactory(ctx);
        LazyHelpManagerFactory helpManagerFactory = new LazyHelpManagerFactory();
        SidebarManagerFactory sidebarManagerFactory = new LazySidebarManagerFactory();
        UIFactory uiFactory = new UIFactory(presenterFactory, viewFactory, windowFactory,
                helpManagerFactory, sidebarManagerFactory);
        log.debug("storing..");
        INSTANCES.put(vaadinSession.getSession().getId(), uiFactory);
        log.debug("instance will be returned");
        return uiFactory;
    }

    public static void free(VaadinSession vaadinSession) {
        if (INSTANCES.containsKey(vaadinSession.getSession().getId())) {
            INSTANCES.remove(vaadinSession.getSession().getId());
        }
    }
}
