package ua.org.tees.yarosh.tais.ui.listeners.backend;

import com.vaadin.server.VaadinSession;
import ua.org.tees.yarosh.tais.ui.components.windows.ChatWindow;
import ua.org.tees.yarosh.tais.ui.core.UIFactory;
import ua.org.tees.yarosh.tais.ui.core.api.Registrants;
import ua.org.tees.yarosh.tais.user.comm.ChatMessage;

import javax.jms.Message;
import javax.jms.MessageListener;

/**
 * @author Timur Yarosh
 *         Date: 23.04.14
 *         Time: 20:04
 */
public class ChatListener implements MessageListener {

    private VaadinSession vaadinSession;

    private ChatListener(VaadinSession vaadinSession) {
        this.vaadinSession = vaadinSession;
    }

    public static ChatListener createListener(VaadinSession vaadinSession) {
        if (vaadinSession == null) {
            throw new IllegalArgumentException("VaadinSession can't be null");
        }
        return new ChatListener(vaadinSession);
    }

    @Override
    public void onMessage(Message message) {
        if (forMe((ChatMessage) message)) {
            ChatMessage chatMessage = (ChatMessage) message;

            ChatWindow window = UIFactory.getCurrent().getWindow(ChatWindow.class);
            window.setDestination(chatMessage.getFrom());
            window.addCompanionMessage((ChatMessage) message);

            vaadinSession.getUIs().forEach(u -> u.addWindow(window));
        }
    }

    private boolean forMe(ChatMessage message) {
        return message.getTo().equals(Registrants.getCurrent(vaadinSession).getLogin());
    }
}
