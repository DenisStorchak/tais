package ua.org.tees.yarosh.tais.user.comm;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.eventbus.AsyncEventBus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.org.tees.yarosh.tais.user.comm.api.ChatMessageReceivedListener;
import ua.org.tees.yarosh.tais.user.comm.api.JmsBroadcastService;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import java.io.IOException;

@Service
public class JmsBroadcaster implements JmsBroadcastService {

    public static final Logger log = LoggerFactory.getLogger(JmsBroadcaster.class);

    private AsyncEventBus eventBus;

    @Autowired
    public void setEventBus(AsyncEventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    public void onMessage(Message message) {
        try {
            TextMessage textMessage = (TextMessage) message;
            ChatMessage chatMessage = new ObjectMapper().readValue(textMessage.getText(), ChatMessage.class); //todo OM
            eventBus.post(new ChatMessageReceivedEvent(chatMessage));
        } catch (IOException | ClassCastException | JMSException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public synchronized void addListener(ChatMessageReceivedListener listener) {
        eventBus.register(listener);
    }
}
