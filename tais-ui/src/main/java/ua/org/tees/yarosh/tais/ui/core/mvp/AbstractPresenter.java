package ua.org.tees.yarosh.tais.ui.core.mvp;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.server.VaadinSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.org.tees.yarosh.tais.ui.core.HelpManager;
import ua.org.tees.yarosh.tais.ui.core.SessionKeys;

/**
 * @author Timur Yarosh
 */
@SuppressWarnings("unchecked")
public abstract class AbstractPresenter implements Presenter {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractPresenter.class);
    private PresenterBasedView view;
    private HelpManager helpManager;

    @Override
    public HelpManager getHelpManager() {
        return helpManager;
    }

    public AbstractPresenter(PresenterBasedView view, HelpManager helpManager) {
        LOGGER.info("AbstractPresenter instance created, view will be initialized now");
        this.view = view;
        this.helpManager = helpManager;
        getView().setPrimaryPresenter(this);
        getHelpManager().closeAll();
    }

    @Override
    public PresenterBasedView getView() {
        return view;
    }

    @Override
    public <V extends View> V getView(Class<V> viewClazz) {
        return (V) getView();
    }

    @Override
    public void navigateBack(Navigator navigator) {
        navigator.navigateTo((String) VaadinSession.getCurrent().getAttribute(SessionKeys.LAST_VIEW));
    }

    protected void info(String message, Object... params) {
        LOGGER.info(message, params);
    }

    @Override
    public void updateData() { /*NOP*/ }
}
