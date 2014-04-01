package ua.org.tees.yarosh.tais.ui.core;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.ServiceException;
import com.vaadin.server.SessionInitEvent;
import com.vaadin.server.SessionInitListener;
import com.vaadin.server.VaadinServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.support.WebApplicationContextUtils;
import ua.org.tees.yarosh.tais.ui.TAISUI;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import static ua.org.tees.yarosh.tais.ui.core.text.SessionKeys.UI_FACTORY;

@WebServlet(urlPatterns = "/*", asyncSupported = true)
@VaadinServletConfiguration(productionMode = true, ui = TAISUI.class)
public class ApplicationServlet extends VaadinServlet implements SessionInitListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationServlet.class);

    protected void servletInitialized() throws ServletException {
        super.servletInitialized();
        getService().addSessionInitListener(this);
    }

    @Override
    public void sessionInit(SessionInitEvent event) throws ServiceException {
        ComponentFactory componentFactory = WebApplicationContextUtils.getRequiredWebApplicationContext(
                getCurrent().getServletContext()).getBean(ComponentFactory.class);
        LOGGER.debug("ComponentFactory created for session {}", event.getSession().getSession().getId());
        event.getSession().setAttribute(UI_FACTORY, componentFactory);
    }
}
