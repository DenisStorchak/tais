package ua.org.tees.yarosh.tais.user.comm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ua.org.tees.yarosh.tais.user.comm.api.JmsBroadcaster;

import javax.jms.Message;
import javax.jms.MessageListener;
import java.util.ArrayList;
import java.util.List;

@Service
public class JmsBroadcastService implements JmsBroadcaster {

    public static final Logger log = LoggerFactory.getLogger(JmsBroadcastService.class);
    private List<MessageListener> subscribers = new ArrayList<>();

    @Override
    public void onMessage(Message message) {
        log.debug("JMS Message received, broadcasting started");
        for (MessageListener subscriber : subscribers) {
            subscriber.onMessage(message);
        }
        log.debug("broadcasting finished");
    }

    @Override
    public synchronized void subscribe(MessageListener listener) {
        log.debug("subscriber added");
        subscribers.add(listener);
    }
}
