package ua.org.tees.yarosh.tais.ui.listeners;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinSession;
import ua.org.tees.yarosh.tais.auth.AuthManager;

import static ua.org.tees.yarosh.tais.ui.core.api.DataBinds.UriFragments.ACCESS_DENIED;

public class ViewAccessGuard implements ViewChangeListener {

    @Override
    public boolean beforeViewChange(ViewChangeEvent viewChangeEvent) {
        Class<? extends View> viewClazz = viewChangeEvent.getNewView().getClass();
        if (!AuthManager.isAuthorized(VaadinSession.getCurrent().getSession().getId(), viewClazz)) {
            viewChangeEvent.getNavigator().navigateTo(ACCESS_DENIED);
            return false;
        }
        return true;
    }

    @Override
    public void afterViewChange(ViewChangeEvent viewChangeEvent) {
    }
}
