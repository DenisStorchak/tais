package ua.org.tees.yarosh.tais.ui.core.mvp;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.server.VaadinSession;
import ua.org.tees.yarosh.tais.ui.SessionAttributes;

import java.util.logging.Logger;

/**
 * @author Timur Yarosh
 */
public abstract class AbstractPresenter {
    private static final Logger log = Logger.getLogger(AbstractPresenter.class.getName());
    private View view;

    public AbstractPresenter(View view) {
        log.info("AbstractPresenter instance created, view will be initialized now");
        this.view = view;
        initView();
    }

    public View getView() {
        return view;
    }

    public void navigateBack(Navigator navigator) {
        navigator.navigateTo((String) VaadinSession.getCurrent().getAttribute(SessionAttributes.LAST_VIEW));
    }

    protected abstract void initView();
}
