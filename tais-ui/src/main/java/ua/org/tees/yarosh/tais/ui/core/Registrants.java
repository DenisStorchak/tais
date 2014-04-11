package ua.org.tees.yarosh.tais.ui.core;

import com.vaadin.server.VaadinServlet;
import ua.org.tees.yarosh.tais.core.user.mgmt.api.service.RegistrantService;

import static org.springframework.web.context.support.WebApplicationContextUtils.getRequiredWebApplicationContext;
import static ua.org.tees.yarosh.tais.ui.core.DataBinds.Cookies.AUTH;

public abstract class Registrants {

    private static RegistrantService registrantService = getRequiredWebApplicationContext(
            VaadinServlet.getCurrent().getServletContext()).getBean(RegistrantService.class);

    public static ua.org.tees.yarosh.tais.core.common.models.Registrant getCurrent() {
        String login = VaadinUtils.getCookie(AUTH).getValue();
        return registrantService.getRegistration(login);
    }
}
