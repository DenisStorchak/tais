package ua.org.tees.yarosh.tais.user.comm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ua.org.tees.yarosh.tais.user.comm.api.JmsBroadcaster;

import javax.jms.Message;
import javax.jms.MessageListener;
import java.util.Vector;

@Service
public class JmsBroadcastService implements JmsBroadcaster {

    public static final Logger log = LoggerFactory.getLogger(JmsBroadcastService.class);
    private Vector<MessageListener> subscribers = new Vector<>();

    @Override
    public void onMessage(Message message) {
        log.debug("JMS Message received, broadcasting started");
        subscribers.forEach(l -> l.onMessage(message));
        log.debug("broadcasting finished");
    }

    @Override
    public void subscribe(MessageListener listener) {
        log.debug("subscriber added");
        subscribers.add(listener);
    }
}
