package ua.org.tees.yarosh.tais.user.comm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import ua.org.tees.yarosh.tais.core.common.api.Message;
import ua.org.tees.yarosh.tais.user.comm.api.Communicator;

import javax.jms.Topic;

@Service
@Qualifier(ChatService.QUALIFIER)
public class ChatService implements Communicator {

    public static final String QUALIFIER = "chatService";
    private JmsTemplate jmsTemplate;
    private Topic chatTopic;

    @Autowired
    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @Autowired
    public void setChatTopic(Topic chatTopic) {
        this.chatTopic = chatTopic;
    }

    @Override
    public void sendMessage(Message message) {
        jmsTemplate.convertAndSend(chatTopic, message);
    }
}
