package ua.org.tees.yarosh.tais.user.comm.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import ua.org.tees.yarosh.tais.core.common.CoreConfiguration;
import ua.org.tees.yarosh.tais.core.common.properties.MailProperties;

import java.util.Properties;

/**
 * @author Timur Yarosh
 *         Date: 09.03.14
 *         Time: 22:42
 */
@Configuration
@Import(CoreConfiguration.class)
public class MailConfiguration {
    private static final String MAIL_SMTP_AUTH_JMAIL_PROPERTY_NAME = "mail.smtp.auth";
    private static final String MAIL_SMTP_SSL_ENABLE_JMAIL_PROPERTY_NAME = "mail.smtp.ssl.enable";
    private static final String DEFAULT_MAIL_SUBJECT = "TEES Automatic Interaction System";

    @Autowired
    private MailProperties mailProperties;

    @Bean
    public JavaMailSenderImpl mailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setJavaMailProperties(new Properties());

        mailSender.setHost(mailProperties.getHost());
        mailSender.setPort(mailProperties.getPort());
        mailSender.setUsername(mailProperties.getUsername());
        mailSender.setPassword(mailProperties.getPassword());
        mailSender.getJavaMailProperties().setProperty(MAIL_SMTP_AUTH_JMAIL_PROPERTY_NAME, mailProperties.getMailSmtpAuth());
        mailSender.getJavaMailProperties().setProperty(MAIL_SMTP_SSL_ENABLE_JMAIL_PROPERTY_NAME, mailProperties.getMailSmtpSslEnable());
        return mailSender;
    }

    @Bean
    public SimpleMailMessage preparedMailMessage() {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(mailProperties.getUsername());
        simpleMailMessage.setSubject(DEFAULT_MAIL_SUBJECT);
        return simpleMailMessage;
    }
}
