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
import ua.org.tees.yarosh.tais.ui.core.api.UIContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import static ua.org.tees.yarosh.tais.ui.core.DataBinds.SessionKeys.COMPONENT_FACTORY;

@WebServlet(urlPatterns = "/*", asyncSupported = true)
@VaadinServletConfiguration(productionMode = true, ui = TAISUI.class)
public class ApplicationServlet extends VaadinServlet implements SessionInitListener {

    private static final Logger log = LoggerFactory.getLogger(ApplicationServlet.class);

    protected void servletInitialized() throws ServletException {
        super.servletInitialized();
        getService().addSessionInitListener(this);
    }

    @Override
    public void sessionInit(SessionInitEvent event) throws ServiceException {
        UIContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(
                getCurrent().getServletContext()).getBean(UIContext.class);
        UIFactory uiFactory = UIFactory.createFactory(ctx);
        log.debug("ComponentFactory created for session {}", event.getSession().getSession().getId());
        event.getSession().setAttribute(COMPONENT_FACTORY, uiFactory);
        event.getSession().addRequestHandler((session, request, response) -> {
            log.debug("Request from [{}] handled", request.getRemoteHost());
            return false;
        });
    }
}
