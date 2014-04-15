package ua.org.tees.yarosh.tais.homework;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.context.annotation.*;
import ua.org.tees.yarosh.tais.core.common.CommonConfiguration;
import ua.org.tees.yarosh.tais.core.user.mgmt.UserMgmtConfiguration;

@Configuration
@Import({CommonConfiguration.class, UserMgmtConfiguration.class})
@ComponentScan(basePackageClasses = HomeworkConfiguration.class)
@EnableAspectJAutoProxy
public class HomeworkConfiguration {

    @Bean
    public CachingConnectionFactory connectionFactory() {
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory("localhost");
        cachingConnectionFactory.setUsername("username");
        cachingConnectionFactory.setPassword("password");
        cachingConnectionFactory.setPort(5672);
        return cachingConnectionFactory;
    }
}
