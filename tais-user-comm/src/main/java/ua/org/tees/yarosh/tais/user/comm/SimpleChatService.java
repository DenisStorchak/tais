package ua.org.tees.yarosh.tais.user.comm;

import com.google.common.eventbus.AsyncEventBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ua.org.tees.yarosh.tais.core.common.api.Listener;
import ua.org.tees.yarosh.tais.core.common.api.Message;
import ua.org.tees.yarosh.tais.user.comm.api.Communicator;

import java.util.Vector;

@Service
@Qualifier(SimpleChatService.QUALIFIER)
public class SimpleChatService implements Communicator {

    public static final String QUALIFIER = "simpleChatService";
    private AsyncEventBus eventBus;
    private static final Vector<Message> UNDELIVERED = new Vector<>();

    @Autowired
    public void setEventBus(AsyncEventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    public void sendMessage(Message message) {
        UNDELIVERED.add(message);
        eventBus.post(new ChatMessageReceivedEvent((ChatMessage) message, this));
    }

    @Override
    public void addSentListener(Listener listener) {
        eventBus.register(listener);
    }

    @Override
    public void markDelivered(Message message) {
        if (!UNDELIVERED.contains(message)) {
            throw new IllegalStateException("Message not found");
        }
        UNDELIVERED.remove(message);
    }
}
