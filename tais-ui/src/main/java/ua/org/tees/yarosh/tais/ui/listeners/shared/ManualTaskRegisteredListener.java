package ua.org.tees.yarosh.tais.ui.listeners.shared;

import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Button;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.org.tees.yarosh.tais.core.common.RegexUtils;
import ua.org.tees.yarosh.tais.core.common.models.Registrant;
import ua.org.tees.yarosh.tais.homework.models.ManualTask;
import ua.org.tees.yarosh.tais.ui.core.Registrants;
import ua.org.tees.yarosh.tais.ui.listeners.SidebarManager;
import ua.org.tees.yarosh.tais.ui.views.student.UnresolvedTasksView;

import java.util.regex.Pattern;

import static ua.org.tees.yarosh.tais.homework.api.HomeworkManager.ManualTaskEnabledListener;

public class ManualTaskRegisteredListener implements ManualTaskEnabledListener {

    private static final Logger log = LoggerFactory.getLogger(ManualTaskRegisteredListener.class);
    private SidebarManager sidebarManager;
    private VaadinSession session;

    public ManualTaskRegisteredListener(SidebarManager sidebarManager, VaadinSession session) {
        this.sidebarManager = sidebarManager;
        this.session = session;
    }

    @Override
    public void onEnabled(ManualTask manualTask) {
        log.debug("ManualTaskRegisteredEvent handler invoked");
        Registrant registrant = Registrants.getCurrent(session);
        if (registrant != null && manualTask.getStudentGroup().equals(registrant.getGroup())) {
            log.debug("Registrant [{}] session affected", registrant.toString());
            Button button = sidebarManager.getSidebar().getSidebarMenu().getButton(UnresolvedTasksView.class);
            if (button != null) {
                log.debug("Old button caption is [{}]", button.getCaption());
                String badge = RegexUtils.substringMatching(button.getCaption(), Pattern.compile(".*(\\d+).*"));
                int newValue = Integer.valueOf(badge) + 1;
                button.setCaption(button.getCaption().replaceAll("\\d+", String.valueOf(newValue)));
                log.debug("New button caption is [{}]", button.getCaption());
            } else {
                log.warn("Button not found");
            }
        } else {
            log.debug("Current registrant is null, so handler is resting");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ManualTaskRegisteredListener that = (ManualTaskRegisteredListener) o;

        if (!session.equals(that.session)) return false;
        if (!sidebarManager.equals(that.sidebarManager)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = sidebarManager.hashCode();
        result = 31 * result + session.hashCode();
        return result;
    }
}
