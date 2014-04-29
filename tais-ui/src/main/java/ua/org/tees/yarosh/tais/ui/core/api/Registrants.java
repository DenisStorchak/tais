package ua.org.tees.yarosh.tais.ui.core.api;

import com.vaadin.server.VaadinServlet;
import com.vaadin.server.VaadinSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.org.tees.yarosh.tais.auth.UserDetails;
import ua.org.tees.yarosh.tais.core.common.models.Registrant;
import ua.org.tees.yarosh.tais.core.user.mgmt.api.service.RegistrantService;

import static org.springframework.web.context.support.WebApplicationContextUtils.getRequiredWebApplicationContext;
import static ua.org.tees.yarosh.tais.auth.AuthManager.getUserDetails;

public abstract class Registrants {

    private static final Logger log = LoggerFactory.getLogger(Registrants.class);

    private static RegistrantService registrantService = getRequiredWebApplicationContext(
            VaadinServlet.getCurrent().getServletContext()).getBean(RegistrantService.class);

//    public static Registrant getCurrent() {
//        return getCurrent(VaadinSession.getCurrent());
//    }


    /**
     * Extract registrant from session
     *
     * @param session
     * @return
     */
    public static Registrant getCurrent(VaadinSession session) {
        if (session == null) {
            log.error("Current session is null, null will be returned");
            return null;
        }
        UserDetails userDetails = getUserDetails(session.getSession().getId());
        if (userDetails == null) return null; //todo throw unchecked
        return registrantService.getRegistration(userDetails.getUsername());
    }

    public static Registrant getCurrent() {
        return getCurrent(VaadinSession.getCurrent());
    }
}
