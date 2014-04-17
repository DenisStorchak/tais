package ua.org.tees.yarosh.tais.ui.core.api;

import com.vaadin.server.VaadinServlet;
import com.vaadin.server.VaadinSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.org.tees.yarosh.tais.core.common.models.Registrant;
import ua.org.tees.yarosh.tais.core.user.mgmt.api.service.RegistrantService;

import static org.springframework.web.context.support.WebApplicationContextUtils.getRequiredWebApplicationContext;
import static ua.org.tees.yarosh.tais.ui.core.api.DataBinds.SessionKeys.REGISTRANT_ID;

public abstract class Registrants {

    private static final Logger log = LoggerFactory.getLogger(Registrants.class);

    private static RegistrantService registrantService = getRequiredWebApplicationContext(
            VaadinServlet.getCurrent().getServletContext()).getBean(RegistrantService.class);

    public static Registrant getCurrent() {
        return getCurrent(VaadinSession.getCurrent());
    }


    public static Registrant getCurrent(VaadinSession session) {
        if (session == null) {
            log.debug("Current session is null, null will be returned");
            return null;
        }
        Object attribute = session.getAttribute(REGISTRANT_ID);
        if (attribute != null) {
            String login = (String) attribute;
            log.debug("login [{}] found in session", login);
            return registrantService.getRegistration(login);
        } else {
            log.debug("login not found in session");
            return null;
        }
    }
}
