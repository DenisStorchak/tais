package ua.org.tees.yarosh.tais.user.comm.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.UrlResource;
import org.springframework.jndi.support.SimpleJndiBeanFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.net.MalformedURLException;
import java.net.URI;
import java.util.Properties;

/**
 * @author Timur Yarosh
 *         Date: 09.03.14
 *         Time: 22:42
 */
@Configuration
public class MailConfiguration {
    private static final String MAIL_SMTP_AUTH_JMAIL_PROPERTY_NAME = "mail.smtp.auth";
    private static final String MAIL_SMTP_SSL_ENABLE_JMAIL_PROPERTY_NAME = "mail.smtp.ssl.enable";
    @Value("${host}")
    private String host;
    @Value("${port}")
    private int port;
    @Value("${username}")
    private String username;
    @Value("${password}")
    private String password;
    @Value("${mail.smtp.auth}")
    private String mailSmtpAuth;
    @Value("${mail.smtp.ssl.enable}")
    private String mailSmtpSslEnable;

    public JavaMailSenderImpl mailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setJavaMailProperties(new Properties());

        mailSender.setHost(host);
        mailSender.setPort(port);
        mailSender.setUsername(username);
        mailSender.setPassword(password);
        mailSender.getJavaMailProperties().setProperty(MAIL_SMTP_AUTH_JMAIL_PROPERTY_NAME, mailSmtpAuth);
        mailSender.getJavaMailProperties().setProperty(MAIL_SMTP_SSL_ENABLE_JMAIL_PROPERTY_NAME, mailSmtpSslEnable);
        return mailSender;
    }

    @Bean
    public SimpleMailMessage preparedMailMessage() {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(username);
        return simpleMailMessage;
    }

    @Bean
    public SimpleJndiBeanFactory simpleJndiBeanFactory() {
        return new SimpleJndiBeanFactory();
    }

    @Bean
    public PropertyPlaceholderConfigurer getConfigurer() throws MalformedURLException {
        PropertyPlaceholderConfigurer configurer = new PropertyPlaceholderConfigurer();
        configurer.setLocation(new UrlResource(simpleJndiBeanFactory().getBean("mail.config", URI.class)));
        return configurer;
    }
}
