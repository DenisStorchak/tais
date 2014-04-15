package ua.org.tees.yarosh.tais.ui.listeners.shared;

import com.google.common.eventbus.Subscribe;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Button;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.org.tees.yarosh.tais.core.common.RegexUtils;
import ua.org.tees.yarosh.tais.core.common.models.Registrant;
import ua.org.tees.yarosh.tais.homework.events.ManualTaskRegisteredEvent;
import ua.org.tees.yarosh.tais.ui.core.Registrants;
import ua.org.tees.yarosh.tais.ui.listeners.SidebarManager;
import ua.org.tees.yarosh.tais.ui.views.student.UnresolvedTasksView;

import java.util.regex.Pattern;

public class ManualTaskRegisteredListener {

    private static final Logger log = LoggerFactory.getLogger(ManualTaskRegisteredListener.class);
    private SidebarManager sidebarManager;
    private VaadinSession session;

    public ManualTaskRegisteredListener(SidebarManager sidebarManager, VaadinSession session) {
        this.sidebarManager = sidebarManager;
        this.session = session;
    }

    @Subscribe
    public void onManualTaskRegisteredEvent(ManualTaskRegisteredEvent event) throws InterruptedException {
        log.debug("ManualTaskRegisteredEvent handler invoked");
        Registrant registrant = Registrants.getCurrent(session);
        if (registrant != null && event.getTask().getStudentGroup().equals(registrant.getGroup())) {
            log.debug("Registrant [{}] session affected", registrant.toString());
            log.debug("Groups matched, badge value will be incremented");
            Button button = sidebarManager.getSidebar().getSidebarMenu().getButton(UnresolvedTasksView.class);
            log.debug("Old button caption is [{}]", button.getCaption());
            String badge = RegexUtils.substringMatching(button.getCaption(), Pattern.compile(".*(\\d+).*"));
            int newValue = Integer.valueOf(badge) + 1;
            button.setCaption(button.getCaption().replaceAll("\\d+", String.valueOf(newValue)));
            log.debug("New button caption is [{}]", button.getCaption());
        } else {
            log.debug("Current registrant is null, so handler is resting");
        }
    }
}
