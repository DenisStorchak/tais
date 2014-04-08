package ua.org.tees.yarosh.tais.ui.core.mvp;

import com.vaadin.navigator.View;
import com.vaadin.server.VaadinSession;
import ua.org.tees.yarosh.tais.ui.core.api.ComponentFactory;

import static com.vaadin.navigator.Navigator.ClassBasedViewProvider;
import static ua.org.tees.yarosh.tais.ui.core.DataBinds.SessionKeys.COMPONENT_FACTORY;

/**
 * @author Timur Yarosh
 *         Date: 25.03.14
 *         Time: 22:09
 */
public class FactoryBasedViewProvider extends ClassBasedViewProvider {
    /**
     * Create a new view provider which creates new view instances based on
     * a view class.
     *
     * @param viewName  name of the views to create (not null)
     * @param viewClass
     */
    public FactoryBasedViewProvider(String viewName, Class<? extends View> viewClass) {
        super(viewName, viewClass);
    }

    @Override
    public View getView(String viewName) {
        ComponentFactory componentFactory = (ComponentFactory) VaadinSession.getCurrent()
                .getAttribute(COMPONENT_FACTORY);
        componentFactory.getHelpManager().closeAll();
        return componentFactory.getView(getViewClass());
    }
}
