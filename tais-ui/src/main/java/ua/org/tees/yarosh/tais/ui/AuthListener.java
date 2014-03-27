package ua.org.tees.yarosh.tais.ui;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinSession;
import ua.org.tees.yarosh.tais.auth.AuthManager;
import ua.org.tees.yarosh.tais.ui.core.constants.SessionKeys;
import ua.org.tees.yarosh.tais.ui.core.constants.UriFragments;

public class AuthListener implements ViewChangeListener {

    @Override
    public boolean beforeViewChange(ViewChangeEvent viewChangeEvent) {
        Class<? extends View> viewClazz = viewChangeEvent.getNewView().getClass();
        String login = (String) VaadinSession.getCurrent().getAttribute(SessionKeys.REGISTRANT_ID);
        if (!AuthManager.isAuthorized(login, viewClazz)) {
            viewChangeEvent.getNavigator().navigateTo(UriFragments.ACCESS_DENIED);
            return false;
        }
        return true;
    }

    @Override
    public void afterViewChange(ViewChangeEvent viewChangeEvent) {
    }
}
