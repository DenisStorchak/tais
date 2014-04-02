package ua.org.tees.yarosh.tais.ui.core;

import com.vaadin.server.VaadinSession;

import static ua.org.tees.yarosh.tais.ui.core.DataBinds.SessionKeys.COMPONENT_FACTORY;

public class SessionFactory {
    public static ComponentFactory getCurrent() {
        return (ComponentFactory) VaadinSession.getCurrent().getAttribute(COMPONENT_FACTORY);
    }
}
