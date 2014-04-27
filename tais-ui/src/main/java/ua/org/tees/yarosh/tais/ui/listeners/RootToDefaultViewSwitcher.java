package ua.org.tees.yarosh.tais.ui.listeners;

import com.vaadin.navigator.ViewChangeListener;
import ua.org.tees.yarosh.tais.core.common.models.Registrant;
import ua.org.tees.yarosh.tais.ui.TAISUI;
import ua.org.tees.yarosh.tais.ui.core.ViewResolver;
import ua.org.tees.yarosh.tais.ui.core.api.Registrants;

public class RootToDefaultViewSwitcher implements ViewChangeListener {
    @Override
    public boolean beforeViewChange(ViewChangeListener.ViewChangeEvent event) {
        if (event.getViewName().isEmpty()) {
            Registrant registrant = Registrants.getCurrent();
            if (registrant == null) {
                return true;
            } else {
                String defaultState = ViewResolver.resolveDefaultView(registrant);
                TAISUI.navigateTo(defaultState);
                return false;
            }
        }
        return true;
    }

    @Override
    public void afterViewChange(ViewChangeListener.ViewChangeEvent event) {

    }
}
