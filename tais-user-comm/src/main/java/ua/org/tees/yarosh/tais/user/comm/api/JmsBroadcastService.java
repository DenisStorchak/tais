package ua.org.tees.yarosh.tais.user.comm.api;

import javax.jms.MessageListener;

public interface JmsBroadcastService extends MessageListener {
    void addListener(ChatMessageReceivedListener listener);
}
