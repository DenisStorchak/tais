package ua.org.tees.yarosh.tais.ui.core;

import com.vaadin.navigator.View;
import com.vaadin.server.VaadinServlet;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Window;
import org.springframework.web.context.support.WebApplicationContextUtils;
import ua.org.tees.yarosh.tais.ui.components.HelpManager;
import ua.org.tees.yarosh.tais.ui.core.api.*;
import ua.org.tees.yarosh.tais.ui.core.mvp.*;
import ua.org.tees.yarosh.tais.ui.listeners.SidebarManager;

import java.io.Serializable;

import static ua.org.tees.yarosh.tais.ui.core.api.DataBinds.SessionKeys.COMPONENT_FACTORY;

public class UIFactory implements ComponentFactory, Serializable {
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
        ComponentFactory instance = (ComponentFactory) VaadinSession.getCurrent().getAttribute(COMPONENT_FACTORY);
        if (instance == null) {
            instance = createAndSaveFactory();
        }
        return instance;
    }

    /**
     * Create factory and save it to current vaadin session
     */
    private static ComponentFactory createAndSaveFactory() {
        UIContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(
                VaadinServlet.getCurrent().getServletContext()).getBean(UIContext.class);
        ContextPresenterFactory presenterFactory = new ContextPresenterFactory(ctx);
        ContextViewFactory viewFactory = new ContextViewFactory(presenterFactory);
        ContextWindowFactory windowFactory = new ContextWindowFactory(ctx);
        LazyHelpManagerFactory helpManagerFactory = new LazyHelpManagerFactory();
        SidebarManagerFactory sidebarManagerFactory = new LazySidebarManagerFactory();
        UIFactory uiFactory = new UIFactory(presenterFactory, viewFactory, windowFactory,
                helpManagerFactory, sidebarManagerFactory);
        VaadinSession.getCurrent().setAttribute(COMPONENT_FACTORY, uiFactory);
        return uiFactory;
    }
}
