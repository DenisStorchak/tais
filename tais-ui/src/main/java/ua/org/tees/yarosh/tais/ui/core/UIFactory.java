package ua.org.tees.yarosh.tais.ui.core;

import com.google.common.eventbus.AsyncEventBus;
import com.vaadin.navigator.View;
import com.vaadin.ui.Window;
import ua.org.tees.yarosh.tais.ui.components.HelpManager;
import ua.org.tees.yarosh.tais.ui.core.api.*;
import ua.org.tees.yarosh.tais.ui.core.mvp.*;
import ua.org.tees.yarosh.tais.ui.listeners.SidebarManager;

public class UIFactory implements ComponentFactory {
    private ViewFactory viewFactory;
    private WindowFactory windowFactory;
    private PresenterFactory presenterFactory;
    private HelpManagerFactory helpManagerFactory;
    private SidebarManagerFactory sidebarManagerFactory;
    private EventbusFactory eventbusFactory;

    private UIFactory(PresenterFactory presenterFactory,
                      ViewFactory viewFactory,
                      WindowFactory windowFactory,
                      HelpManagerFactory helpManagerFactory,
                      SidebarManagerFactory sidebarManagerFactory,
                      EventbusFactory eventbusFactory) {
        this.viewFactory = viewFactory;
        this.windowFactory = windowFactory;
        this.presenterFactory = presenterFactory;
        this.helpManagerFactory = helpManagerFactory;
        this.sidebarManagerFactory = sidebarManagerFactory;
        this.eventbusFactory = eventbusFactory;
    }

    public static UIFactory createFactory(UIContext ctx) {
        ContextPresenterFactory presenterFactory = new ContextPresenterFactory(ctx);
        ContextViewFactory viewFactory = new ContextViewFactory(presenterFactory);
        ContextWindowFactory windowFactory = new ContextWindowFactory(ctx);
        LazyHelpManagerFactory helpManagerFactory = new LazyHelpManagerFactory();
        SidebarManagerFactory sidebarManagerFactory = new LazySidebarManagerFactory();
        EventbusFactory eventbusFactory = new ContextEventbusFactory();
        return new UIFactory(presenterFactory, viewFactory, windowFactory,
                helpManagerFactory, sidebarManagerFactory, eventbusFactory);
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

    @Override
    public AsyncEventBus getEventBus() {
        return eventbusFactory.getEventBus();
    }
}
