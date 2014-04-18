package ua.org.tees.yarosh.tais.ui.core;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.ServiceException;
import com.vaadin.server.SessionInitEvent;
import com.vaadin.server.SessionInitListener;
import com.vaadin.server.VaadinServlet;
import org.springframework.web.context.support.WebApplicationContextUtils;
import ua.org.tees.yarosh.tais.ui.TAISUI;
import ua.org.tees.yarosh.tais.ui.core.api.UIContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

@WebServlet(urlPatterns = "/*", asyncSupported = true)
@VaadinServletConfiguration(productionMode = true, ui = TAISUI.class)
public class ApplicationServlet extends VaadinServlet implements SessionInitListener {

    protected void servletInitialized() throws ServletException {
        super.servletInitialized();
        getService().addSessionInitListener(this);
    }

    @Override
    public void sessionInit(SessionInitEvent event) throws ServiceException {
        UIContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(
                getCurrent().getServletContext()).getBean(UIContext.class);
        UIFactory.createAndSaveFactory(ctx);
    }
}
