package ua.org.tees.yarosh.tais.user.comm.api;

import ua.org.tees.yarosh.tais.core.common.api.Listener;
import ua.org.tees.yarosh.tais.core.common.api.Message;

/**
 * @author Timur Yarosh
 *         Date: 09.03.14
 *         Time: 22:29
 */
public interface Communicator {
    void sendMessage(Message message);

    void addSentListener(Listener listener);

    void markDelivered(Message message);
}
