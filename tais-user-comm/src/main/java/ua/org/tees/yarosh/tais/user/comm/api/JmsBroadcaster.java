package ua.org.tees.yarosh.tais.user.comm.api;

import javax.jms.MessageListener;

public interface JmsBroadcaster extends MessageListener {
    void subscribe(MessageListener listener);
}
