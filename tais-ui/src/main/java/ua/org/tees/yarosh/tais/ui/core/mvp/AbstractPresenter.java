package ua.org.tees.yarosh.tais.ui.core.mvp;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.server.VaadinSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.org.tees.yarosh.tais.ui.SessionAttributes;

/**
 * @author Timur Yarosh
 */
public abstract class AbstractPresenter {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractPresenter.class);
    private View view;

    public AbstractPresenter(View view) {
        LOGGER.info("AbstractPresenter instance created, view will be initialized now");
        this.view = view;
        initView();
    }

    public View getView() {
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
