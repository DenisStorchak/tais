package ua.org.tees.yarosh.tais.user.comm.configuration;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTopic;
import org.apache.activemq.pool.PooledConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.SimpleMessageListenerContainer;
import ua.org.tees.yarosh.tais.user.comm.api.JmsBroadcaster;

import javax.jms.Topic;

@Configuration
@ComponentScan(basePackages = "ua.org.tees.yarosh.tais.user.comm")
public class JmsConfiguration {
    @Bean
    public ActiveMQConnectionFactory activeMQConnectionFactory() {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setBrokerURL("tcp://localhost:61616"); //todo move to jndi config
        connectionFactory.setUserName("admin");
        connectionFactory.setPassword("admin");
        return connectionFactory;
    }

    @Bean
    public PooledConnectionFactory jmsFactory() {
        PooledConnectionFactory connectionFactory = new PooledConnectionFactory();
        connectionFactory.setConnectionFactory(activeMQConnectionFactory());
        return connectionFactory;
    }

    @Bean
    public JmsTemplate jmsTemplate() {
        return new JmsTemplate(jmsFactory());
    }

    @Bean
    public Topic chatTopic() {
        ActiveMQTopic activeMQTopic = new ActiveMQTopic();
        activeMQTopic.setPhysicalName("chatTopic");
        return activeMQTopic;
    }

    @Bean
    @Autowired
    public SimpleMessageListenerContainer simpleMessageListenerContainer(JmsBroadcaster JmsDispatcher) {
        SimpleMessageListenerContainer simpleMessageListenerContainer = new SimpleMessageListenerContainer();
        simpleMessageListenerContainer.setConnectionFactory(jmsFactory());
        simpleMessageListenerContainer.setDestination(chatTopic());
        simpleMessageListenerContainer.setMessageListener(JmsDispatcher);
        return simpleMessageListenerContainer;
    }
}
