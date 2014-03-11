package ua.org.tees.yarosh.tais.user.comm.configuration;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ua.org.tees.yarosh.tais.core.common.configuration.ApplicationConfiguration;
import ua.org.tees.yarosh.tais.core.common.configuration.RabbitMQProperties;
import ua.org.tees.yarosh.tais.core.common.dto.MailMessage;
import ua.org.tees.yarosh.tais.user.comm.EmailCommunicator;
import ua.org.tees.yarosh.tais.user.comm.MailMQService;

@Configuration
@Import(ApplicationConfiguration.class)
@ComponentScan(basePackageClasses = {MailMQService.class})
public class MailRabbitMQConfiguration {

    @Autowired
    private RabbitMQProperties rabbitMQProperties;
    @Autowired
    private EmailCommunicator emailCommunication;

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(rabbitMQProperties.getHost(), rabbitMQProperties.getPort());
        connectionFactory.setUsername(rabbitMQProperties.getUsername());
        connectionFactory.setPassword(rabbitMQProperties.getPassword());
        return connectionFactory;
    }

    @Bean
    public AmqpAdmin amqpAdmin() {
        return new RabbitAdmin(connectionFactory());
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        return new RabbitTemplate(connectionFactory());
    }

    @Bean
    public SimpleMessageListenerContainer simpleMessageListenerContainer() {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory());
        container.setQueues(mailQueue());
        container.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                MailMessage mailMessage = new MailMessage();
                mailMessage.setMessage(message.toString());
                emailCommunication.sendMessage(mailMessage);
            }
        });
        return container;
    }

    @Bean
    @Qualifier("mailQueue")
    public Queue mailQueue() {
        return new Queue(rabbitMQProperties.getMailQueue());
    }
}
