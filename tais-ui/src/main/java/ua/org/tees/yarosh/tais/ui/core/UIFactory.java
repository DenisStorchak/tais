package ua.org.tees.yarosh.tais.ui.core;

import com.vaadin.navigator.View;
import com.vaadin.ui.Window;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import ua.org.tees.yarosh.tais.ui.ListenerFactory;
import ua.org.tees.yarosh.tais.ui.core.mvp.*;

import static ua.org.tees.yarosh.tais.ui.core.bindings.Qualifiers.*;

@Service
@Scope("prototype")
@Qualifier(UI_FACTORY)
public class UIFactory implements ComponentFactory {
    private ViewFactory viewFactory;
    private WindowFactory windowFactory;
    private ListenerFactory listenerFactory;
    private PresenterFactory presenterFactory;
    private HelpManagerFactory helpManagerFactory;

    @Autowired
    public UIFactory(@Qualifier(VIEW_FACTORY) ViewFactory viewFactory,
                     @Qualifier(WINDOW_FACTORY) WindowFactory windowFactory,
                     @Qualifier(LISTENER_FACTORY) ListenerFactory listenerFactory,
                     @Qualifier(PRESENTER_FACTORY) PresenterFactory presenterFactory,
                     @Qualifier(HELP_MANAGER_FACTORY) HelpManagerFactory helpManagerFactory) {
        this.viewFactory = viewFactory;
        this.windowFactory = windowFactory;
        this.listenerFactory = listenerFactory;
        this.presenterFactory = presenterFactory;
        this.helpManagerFactory = helpManagerFactory;
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
    public <L> Object getListener(Class<L> clazz) {
        return listenerFactory.getListener(clazz);
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
