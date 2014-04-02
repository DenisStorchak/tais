package ua.org.tees.yarosh.tais.ui.core;

import com.vaadin.navigator.View;
import com.vaadin.ui.Window;
import ua.org.tees.yarosh.tais.ui.core.mvp.*;

public class UIFactory implements ComponentFactory {
    private ViewFactory viewFactory;
    private WindowFactory windowFactory;
    private PresenterFactory presenterFactory;
    private HelpManagerFactory helpManagerFactory;

    private UIFactory(PresenterFactory presenterFactory,
                      ViewFactory viewFactory,
                      WindowFactory windowFactory,
                      HelpManagerFactory helpManagerFactory) {
        this.viewFactory = viewFactory;
        this.windowFactory = windowFactory;
        this.presenterFactory = presenterFactory;
        this.helpManagerFactory = helpManagerFactory;
    }

    public static UIFactory createFactory(UIContext ctx) {
        ContextPresenterFactory presenterFactory = new ContextPresenterFactory(ctx);
        ContextViewFactory viewFactory = new ContextViewFactory(presenterFactory);
        ContextWindowFactory windowFactory = new ContextWindowFactory(ctx);
        LazyHelpManagerFactory helpManagerFactory = new LazyHelpManagerFactory();
        return new UIFactory(presenterFactory, viewFactory, windowFactory, helpManagerFactory);
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
    public HelpManager getHelpManager() {
        return helpManagerFactory.getHelpManager();
    }
}
