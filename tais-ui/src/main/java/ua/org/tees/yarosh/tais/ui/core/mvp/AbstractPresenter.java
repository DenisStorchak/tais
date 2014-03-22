package ua.org.tees.yarosh.tais.ui.core.mvp;

import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.org.tees.yarosh.tais.ui.core.SessionAttributes;
import ua.org.tees.yarosh.tais.ui.roles.HelpManager;

/**
 * @author Timur Yarosh
 */
public abstract class AbstractPresenter {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractPresenter.class);
    private TaisView view;
    private HelpManager helpManager;

    public HelpManager getHelpManager() {
        return helpManager;
    }

    public AbstractPresenter(TaisView view, HelpManager helpManager) {
        LOGGER.info("AbstractPresenter instance created, view will be initialized now");
        this.view = view;
        this.helpManager = helpManager;
        initView();
    }

    public TaisView getView() {
        return view;
    }

    public void navigateBack(Navigator navigator) {
        navigator.navigateTo((String) VaadinSession.getCurrent().getAttribute(SessionAttributes.LAST_VIEW));
    }

    protected void info(String message, Object... params) {
        LOGGER.info(message, params);
    }

    protected abstract void initView();
}
