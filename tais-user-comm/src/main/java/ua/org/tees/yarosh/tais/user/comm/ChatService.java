package ua.org.tees.yarosh.tais.user.comm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import ua.org.tees.yarosh.tais.core.common.api.Message;
import ua.org.tees.yarosh.tais.user.comm.api.Communicator;

@Service
public class ChatService implements Communicator {

    private JmsTemplate jmsTemplate;

    @Autowired
    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @Override
    public void sendMessage(Message message) {
//        jmsTemplate.setDefaultDestinationName();
    }
}
