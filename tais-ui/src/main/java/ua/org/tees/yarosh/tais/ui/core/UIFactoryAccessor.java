package ua.org.tees.yarosh.tais.ui.core;

import com.vaadin.server.VaadinSession;
import ua.org.tees.yarosh.tais.ui.core.api.ComponentFactory;

import static ua.org.tees.yarosh.tais.ui.core.DataBinds.SessionKeys.COMPONENT_FACTORY;

public class UIFactoryAccessor {
    public static ComponentFactory getCurrent() {
        return (ComponentFactory) VaadinSession.getCurrent().getAttribute(COMPONENT_FACTORY);
    }
}
