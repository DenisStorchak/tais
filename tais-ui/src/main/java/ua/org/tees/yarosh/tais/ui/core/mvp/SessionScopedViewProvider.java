package ua.org.tees.yarosh.tais.ui.core.mvp;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.server.VaadinSession;
import ua.org.tees.yarosh.tais.ui.core.ViewFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Logger;

import static ua.org.tees.yarosh.tais.ui.core.SessionKeys.VIEW_FACTORY;

/**
 * @author Timur Yarosh
 */
public class SessionScopedViewProvider extends Navigator.ClassBasedViewProvider {

    private static Logger log = Logger.getLogger(SessionScopedViewProvider.class.getName());

    /**
     * Create a new view provider which creates new view instances based on
     * a view class.
     *
     * @param viewName  name of the views to create (not null)
     * @param viewClass
     */
    public SessionScopedViewProvider(String viewName, Class<? extends View> viewClass) {
        super(viewName, viewClass);
    }

    public View getView(String viewName) {
        if (!viewName.equals(getViewName())) {
            throw new IllegalArgumentException(String.format("Requested view is %s but this provider provides %s",
                    viewName, getViewName()));
        }
        try {
            ViewFactory viewFactory = (ViewFactory) VaadinSession.getCurrent().getAttribute(VIEW_FACTORY);
            for (Method method : viewFactory.getClass().getMethods()) {
                ProvidesView providesView = method.getAnnotation(ProvidesView.class);
                if (providesView != null && providesView.value().equals(getViewClass())) {

                    return (View) method.invoke(viewFactory);

                }
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            log.info(e.getMessage());
        }
        return null;
    }
}
