package ua.org.tees.yarosh.tais.ui;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

@WebServlet(urlPatterns = "/*", initParams = {
        @WebInitParam(name = "UI", value = "ua.org.tees.yarosh.tais.ui.TaisNavigator")
})
public class ApplicationServlet {
}
