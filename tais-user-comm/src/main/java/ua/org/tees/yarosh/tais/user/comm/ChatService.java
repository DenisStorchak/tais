package ua.org.tees.yarosh.tais.user.comm;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import ua.org.tees.yarosh.tais.core.common.api.Message;
import ua.org.tees.yarosh.tais.user.comm.api.Communicator;

import javax.jms.Topic;

import static java.lang.String.format;

@Service
@Qualifier(ChatService.QUALIFIER)
public class ChatService implements Communicator {

    public static final String QUALIFIER = "chatService";
    public static final Logger log = LoggerFactory.getLogger(ChatService.class);
    private static final String MESSAGE_SENT_LOG_TEMPLATE = "Message [%s] from [%s] sent to [%s]";
    private JmsTemplate jmsTemplate;
    private Topic chatTopic;
    private ObjectMapper objectMapper;

    @Autowired
    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @Autowired
    public void setChatTopic(Topic chatTopic) {
        this.chatTopic = chatTopic;
    }

    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void sendMessage(Message message) {
        ChatMessage chatMessage = (ChatMessage) message;
        try {
            String serializedMessage = objectMapper.writeValueAsString(chatMessage);
            jmsTemplate.send(chatTopic, session -> session.createTextMessage(serializedMessage));
            log.info(format(MESSAGE_SENT_LOG_TEMPLATE,
                    chatMessage.getMessage(),
                    chatMessage.getFrom(),
                    chatMessage.getTo()));
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }
    }
}
