package ua.org.tees.yarosh.tais.user.comm.api;

import ua.org.tees.yarosh.tais.core.common.api.Message;

public interface MQService {
    void put(Message message);
}
