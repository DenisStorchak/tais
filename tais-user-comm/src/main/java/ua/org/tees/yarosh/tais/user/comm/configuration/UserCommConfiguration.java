package ua.org.tees.yarosh.tais.user.comm.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({MailConfiguration.class, JmsConfiguration.class})
public class UserCommConfiguration {
}
