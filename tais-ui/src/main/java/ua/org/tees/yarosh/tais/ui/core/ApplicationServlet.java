package ua.org.tees.yarosh.tais.ui.core;

import com.vaadin.server.VaadinServlet;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

@WebServlet(urlPatterns = "/*", initParams = {
        @WebInitParam(name = "UI", value = "ua.org.tees.yarosh.tais.ui.TAISUI")
})
public class ApplicationServlet extends VaadinServlet {
}
