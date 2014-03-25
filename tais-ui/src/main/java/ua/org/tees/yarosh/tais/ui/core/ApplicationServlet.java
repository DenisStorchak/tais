package ua.org.tees.yarosh.tais.ui.core;

import com.vaadin.server.ServiceException;
import com.vaadin.server.SessionInitEvent;
import com.vaadin.server.SessionInitListener;
import com.vaadin.server.VaadinServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

import static org.springframework.web.context.support.WebApplicationContextUtils.getRequiredWebApplicationContext;
import static ua.org.tees.yarosh.tais.ui.core.SessionKeys.VIEW_FACTORY;

@WebServlet(urlPatterns = "/*", initParams = {
        @WebInitParam(name = "UI", value = "ua.org.tees.yarosh.tais.ui.TAISUI")
})
public class ApplicationServlet extends VaadinServlet implements SessionInitListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationServlet.class);

    protected void servletInitialized() throws ServletException {
        super.servletInitialized();
        getService().addSessionInitListener(this);
    }

    @Override
    public void sessionInit(SessionInitEvent event) throws ServiceException {
        ViewFactory viewFactory = new LazyViewFactory(getRequiredWebApplicationContext(getServletContext()));
        LOGGER.info("ViewFactory created for session {}", event.getSession().getSession().getId());
        event.getSession().setAttribute(VIEW_FACTORY, viewFactory);
    }
}
