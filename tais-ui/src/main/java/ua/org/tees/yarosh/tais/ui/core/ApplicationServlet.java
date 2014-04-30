package ua.org.tees.yarosh.tais.ui.core;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinServlet;
import ua.org.tees.yarosh.tais.ui.TAISUI;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

@WebServlet(urlPatterns = "/*", asyncSupported = true)
@VaadinServletConfiguration(productionMode = true, ui = TAISUI.class)
public class ApplicationServlet extends VaadinServlet {
    @Override
    protected void servletInitialized() throws ServletException {
        super.servletInitialized();
        getService().addSessionInitListener(event -> UIFactory.getCurrent(event.getSession()));
        getService().addSessionDestroyListener(event -> UIFactory.invalidate(event.getSession()));
    }
}
