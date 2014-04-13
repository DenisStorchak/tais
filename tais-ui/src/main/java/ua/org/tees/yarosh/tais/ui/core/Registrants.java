package ua.org.tees.yarosh.tais.ui.core;

import com.vaadin.server.VaadinServlet;
import com.vaadin.server.VaadinSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.org.tees.yarosh.tais.core.user.mgmt.api.service.RegistrantService;

import static org.springframework.web.context.support.WebApplicationContextUtils.getRequiredWebApplicationContext;
import static ua.org.tees.yarosh.tais.ui.core.DataBinds.SessionKeys.REGISTRANT_ID;

public abstract class Registrants {

    private static final Logger log = LoggerFactory.getLogger(Registrants.class);

    private static RegistrantService registrantService = getRequiredWebApplicationContext(
            VaadinServlet.getCurrent().getServletContext()).getBean(RegistrantService.class);

    public static ua.org.tees.yarosh.tais.core.common.models.Registrant getCurrent() {
        String login = (String) VaadinSession.getCurrent().getAttribute(REGISTRANT_ID);
        log.debug("login [{}] found in session", login);
        return registrantService.getRegistration(login);
    }
}
