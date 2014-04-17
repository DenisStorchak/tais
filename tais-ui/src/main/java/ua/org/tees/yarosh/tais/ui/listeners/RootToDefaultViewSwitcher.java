package ua.org.tees.yarosh.tais.ui.listeners;

import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinServlet;
import ua.org.tees.yarosh.tais.core.common.models.Registrant;
import ua.org.tees.yarosh.tais.core.user.mgmt.api.service.RegistrantService;
import ua.org.tees.yarosh.tais.ui.TAISUI;
import ua.org.tees.yarosh.tais.ui.core.ViewResolver;
import ua.org.tees.yarosh.tais.ui.core.api.Registrants;

import static org.springframework.web.context.support.WebApplicationContextUtils.getRequiredWebApplicationContext;

public class RootToDefaultViewSwitcher implements ViewChangeListener {
    @Override
    public boolean beforeViewChange(ViewChangeListener.ViewChangeEvent event) {
        if (event.getViewName().isEmpty()) {
            String login = Registrants.getCurrent().getLogin();
            if (!login.isEmpty()) {
                Registrant registration = getRequiredWebApplicationContext(VaadinServlet.getCurrent()
                        .getServletContext()).getBean(RegistrantService.class).getRegistration(login);
                if (registration == null) {
                    return true;
                }
                String defaultState = ViewResolver.resolveDefaultView(registration);
                TAISUI.navigateTo(defaultState);
                return false;
            }
        }
        return true;
    }

    @Override
    public void afterViewChange(ViewChangeListener.ViewChangeEvent event) {

    }
}
