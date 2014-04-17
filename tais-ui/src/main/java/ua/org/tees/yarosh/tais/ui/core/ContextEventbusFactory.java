package ua.org.tees.yarosh.tais.ui.core;

import com.google.common.eventbus.AsyncEventBus;
import com.vaadin.server.VaadinServlet;
import ua.org.tees.yarosh.tais.ui.core.api.EventbusFactory;

import static org.springframework.web.context.support.WebApplicationContextUtils.getRequiredWebApplicationContext;

public class ContextEventbusFactory implements EventbusFactory {
    @Override
    public AsyncEventBus getEventBus() {
        return getRequiredWebApplicationContext(VaadinServlet.getCurrent().getServletContext()).getBean(AsyncEventBus.class);
    }
}
