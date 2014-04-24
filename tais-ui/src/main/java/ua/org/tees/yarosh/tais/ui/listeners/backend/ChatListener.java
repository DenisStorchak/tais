package ua.org.tees.yarosh.tais.ui.listeners.backend;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.UI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import ua.org.tees.yarosh.tais.ui.components.windows.ChatWindow;
import ua.org.tees.yarosh.tais.ui.core.UIFactory;
import ua.org.tees.yarosh.tais.ui.core.api.Registrants;
import ua.org.tees.yarosh.tais.user.comm.ChatMessage;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import java.io.IOException;

/**
 * @author Timur Yarosh
 *         Date: 23.04.14
 *         Time: 20:04
 */
public class ChatListener implements MessageListener {

    public static final Logger log = LoggerFactory.getLogger(ChatListener.class);
    private VaadinSession vaadinSession;
    private WebApplicationContext ctx;

    private ChatListener(VaadinSession vaadinSession, WebApplicationContext ctx) {
        this.vaadinSession = vaadinSession;
        this.ctx = ctx;
    }

    public static ChatListener createListener(VaadinSession vaadinSession, WebApplicationContext ctx) {
        if (vaadinSession == null) {
            throw new IllegalArgumentException("VaadinSession can't be null");
        }
        return new ChatListener(vaadinSession, ctx);
    }

    @Override
    public void onMessage(Message message) {
        try {
            ChatMessage chatMessage = ctx.getBean(ObjectMapper.class).readValue(message.getBody(String.class), ChatMessage.class); //todo wtf
            if (forMe(chatMessage)) {
                ChatWindow window = UIFactory.getCurrent().getWindow(ChatWindow.class);
                window.setDestination(chatMessage.getFrom());
                window.addCompanionMessage((ChatMessage) message);

                UI ui = UI.getCurrent();
                ui.access(() -> ui.addWindow(window));
            }
        } catch (JMSException e) {
            log.error("Can't get ChatMessage from Message. REASON [{}]", e.getMessage());
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean forMe(ChatMessage message) {
        String login = Registrants.getCurrent(vaadinSession).getLogin();
        return message.getTo().equals(login);
    }
}
