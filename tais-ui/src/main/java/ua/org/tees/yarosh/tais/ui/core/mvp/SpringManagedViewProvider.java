package ua.org.tees.yarosh.tais.ui.core.mvp;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.server.VaadinServlet;
import ua.org.tees.yarosh.tais.ui.configuration.SpringContextHelper;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Timur Yarosh
 */
public class SpringManagedViewProvider extends Navigator.ClassBasedViewProvider {

    private static Logger log = Logger.getLogger(SpringManagedViewProvider.class.getName());

    /**
     * Create a new view provider which creates new view instances based on
     * a view class.
     *
     * @param viewName  name of the views to create (not null)
     * @param viewClass
     */
    public SpringManagedViewProvider(String viewName, Class<? extends View> viewClass) {
        super(viewName, viewClass);
    }

    public View getView(String viewName) {
        if (!viewName.equals(getViewName())) {
            throw new IllegalArgumentException(String.format("Requested view is %s but this provider provides %s",
                    viewName, getViewName()));
        }
        try {
            Presenter presenter;
            Class<? extends Presenter> presenterType = getViewClass().getAnnotation(PresenterClass.class).value();
            if (presenterType == null) {
                log.log(Level.WARNING, "Presenter for {0} view not found, empty view will be returned...", viewName);
                return getViewClass().newInstance();
            }
            presenter = new SpringContextHelper(VaadinServlet.getCurrent().getServletContext()).getBean(presenterType);
            presenter.initView();
            return presenter.getView();
        } catch (InstantiationException | IllegalAccessException e) {
            log.log(Level.SEVERE, "Presenter can't set up the view\n{0}", e.getMessage());
        }
        return null;
    }
}
