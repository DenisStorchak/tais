package ua.org.tees.yarosh.tais.user.comm.api;

import ua.org.tees.yarosh.tais.core.common.dto.Message;

/**
 * @author Timur Yarosh
 *         Date: 09.03.14
 *         Time: 22:29
 */
public interface Communication {
    void sendMessage(Message message);
}
