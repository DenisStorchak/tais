package ua.org.tees.yarosh.tais.ui.listeners.backend;

import com.google.common.eventbus.Subscribe;
import com.vaadin.server.VaadinServlet;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.UI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.org.tees.yarosh.tais.core.common.models.Registrant;
import ua.org.tees.yarosh.tais.ui.components.windows.ChatWindow;
import ua.org.tees.yarosh.tais.ui.core.UIFactory;
import ua.org.tees.yarosh.tais.ui.core.api.Registrants;
import ua.org.tees.yarosh.tais.user.comm.ChatMessage;
import ua.org.tees.yarosh.tais.user.comm.ChatMessageReceivedEvent;
import ua.org.tees.yarosh.tais.user.comm.api.ChatMessageReceivedListener;

/**
 * @author Timur Yarosh
 *         Date: 23.04.14
 *         Time: 20:04
 */
public class ChatListener implements ChatMessageReceivedListener {

    public static final Logger log = LoggerFactory.getLogger(ChatListener.class);
    private VaadinSession vaadinSession;
    private VaadinServlet vaadinServlet;
    private UI ui;

    public ChatListener(VaadinSession vaadinSession, VaadinServlet vaadinServlet, UI ui) {
        this.vaadinSession = vaadinSession;
        this.vaadinServlet = vaadinServlet;
        this.ui = ui;
    }

    @Override
    @Subscribe
    public void onReceived(ChatMessageReceivedEvent event) {
        if (forMe(event.getChatMessage())) {
            log.info("Try to catch chat message");
            ChatWindow window = UIFactory.getCurrent(vaadinSession, vaadinServlet).getWindow(ChatWindow.class);
            ui.access(() -> showMessage(event, window));
        }
    }

    private void showMessage(ChatMessageReceivedEvent event, ChatWindow window) {
        window.setDestination(event.getChatMessage().getFrom());
        window.addCompanionMessage(event.getChatMessage());

        if (!ui.getWindows().contains(window)) {
            ui.addWindow(window);
        }
    }

    private boolean forMe(ChatMessage message) {
        Registrant current = Registrants.getCurrent(vaadinSession);
        return current != null && message.getTo().equals(current.getLogin());
    }
}
