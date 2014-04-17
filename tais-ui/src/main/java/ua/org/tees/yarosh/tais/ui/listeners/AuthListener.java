package ua.org.tees.yarosh.tais.ui.listeners;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinSession;
import ua.org.tees.yarosh.tais.auth.AuthManager;

import static ua.org.tees.yarosh.tais.ui.core.api.DataBinds.SessionKeys.REGISTRANT_ID;
import static ua.org.tees.yarosh.tais.ui.core.api.DataBinds.UriFragments.ACCESS_DENIED;

public class AuthListener implements ViewChangeListener {

    @Override
    public boolean beforeViewChange(ViewChangeEvent viewChangeEvent) {
        Class<? extends View> viewClazz = viewChangeEvent.getNewView().getClass();
        String login = (String) VaadinSession.getCurrent().getAttribute(REGISTRANT_ID);
        if (!AuthManager.isAuthorized(login, viewClazz)) {
            viewChangeEvent.getNavigator().navigateTo(ACCESS_DENIED);
            return false;
        }
        return true;
    }

    @Override
    public void afterViewChange(ViewChangeEvent viewChangeEvent) {
    }
}
