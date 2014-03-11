package ua.org.tees.yarosh.tais.user.comm;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ua.org.tees.yarosh.tais.core.common.api.Message;
import ua.org.tees.yarosh.tais.user.comm.api.MQService;

@Service
public class MailMQService implements MQService {
    @Autowired
    @Qualifier("mailQueue")
    private Queue mailQueue;
    @Autowired
    private AmqpTemplate amqpTemplate;

    @Override
    public void put(Message message) {
        amqpTemplate.convertAndSend(mailQueue.getName(), message);
    }
}
