package ua.org.tees.yarosh.tais.ui.core;

import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinSession;

import static ua.org.tees.yarosh.tais.ui.core.DataBinds.SessionKeys.PREVIOUS_VIEW;

public class LastViewSaver implements ViewChangeListener {
    @Override
    public boolean beforeViewChange(ViewChangeEvent event) {
        VaadinSession.getCurrent().setAttribute(PREVIOUS_VIEW, event.getOldView());
        return true;
    }

    @Override
    public void afterViewChange(ViewChangeEvent event) {
    }
}
