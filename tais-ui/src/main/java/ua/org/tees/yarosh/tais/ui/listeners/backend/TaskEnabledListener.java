package ua.org.tees.yarosh.tais.ui.listeners.backend;

import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.Subscribe;
import com.vaadin.ui.Button;
import com.vaadin.ui.UI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.org.tees.yarosh.tais.core.common.RegexUtils;
import ua.org.tees.yarosh.tais.core.common.models.Registrant;
import ua.org.tees.yarosh.tais.homework.events.ManualTaskEnabledEvent;
import ua.org.tees.yarosh.tais.homework.models.QuestionsSuite;
import ua.org.tees.yarosh.tais.ui.core.UIFactoryAccessor;
import ua.org.tees.yarosh.tais.ui.core.api.Registrants;
import ua.org.tees.yarosh.tais.ui.views.student.UnresolvedTasksView;

import java.util.regex.Pattern;

import static ua.org.tees.yarosh.tais.homework.api.HomeworkManager.ManualTaskEnabledListenerTeacher;
import static ua.org.tees.yarosh.tais.homework.api.HomeworkManager.QuestionsSuiteEnabledListener;

/**
 * @author Timur Yarosh
 *         Date: 17.04.14
 *         Time: 18:54
 */
public class TaskEnabledListener implements ManualTaskEnabledListenerTeacher, QuestionsSuiteEnabledListener {

    private static final Logger log = LoggerFactory.getLogger(TaskEnabledListener.class);
    private UI ui;

    public TaskEnabledListener(UI ui) {
        this.ui = ui;
    }

    @Override
    @Subscribe
    @AllowConcurrentEvents
    public void onEnabled(ManualTaskEnabledEvent event) {
        ui.access(() -> {
            log.debug("ManualTaskEnabledEvent handler invoked");
            Registrant registrant = Registrants.getCurrent(ui.getSession());
            if (registrant != null && event.getTask().getStudentGroup().equals(registrant.getGroup())) {
                log.debug("Registrant [{}] session affected", registrant.toString());
                Button button = UIFactoryAccessor.getCurrent().getSidebarManager().getSidebar()
                        .getSidebarMenu().getButton(UnresolvedTasksView.class);
                if (button != null) {
                    log.debug("Old component caption is [{}]", button.getCaption());
                    String badge = RegexUtils.substringMatching(button.getCaption(), Pattern.compile("(\\d+)"));
                    int newValue = Integer.valueOf(badge) + 1;
                    button.setCaption(button.getCaption().replaceAll("\\d+", String.valueOf(newValue)));
                    log.debug("New component caption is [{}]", button.getCaption());
                } else {
                    log.warn("Button not found");
                }
            } else {
                log.debug("Current registrant is null, so handler is resting");
            }
        });
    }

    @Override
    public void onEnabled(QuestionsSuite questionsSuite) {

    }
}
