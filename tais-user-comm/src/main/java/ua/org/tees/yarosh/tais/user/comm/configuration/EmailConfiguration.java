package ua.org.tees.yarosh.tais.user.comm.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

/**
 * @author Timur Yarosh
 *         Date: 09.03.14
 *         Time: 22:42
 */
@Configuration
public class EmailConfiguration {
    public JavaMailSenderImpl mailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setJavaMailProperties(new Properties());

        mailSender.setHost("smtp.yandex.ru");
        mailSender.setPort(465);
        mailSender.setUsername("no-reply@tees.org.ua");
        mailSender.setPassword("SDG6dsASGvd");
        mailSender.getJavaMailProperties().setProperty("mail.smtp.auth", "true");
        mailSender.getJavaMailProperties().setProperty("mail.smtp.ssl.enable", "true");
        return mailSender;
    }
}
