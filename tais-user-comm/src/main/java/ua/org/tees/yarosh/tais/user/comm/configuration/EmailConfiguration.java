package ua.org.tees.yarosh.tais.user.comm.configuration;

import org.springframework.beans.factory.annotation.Value;
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
    private static final String MAIL_SMTP_AUTH_JMAIL_PROPERTY_NAME = "mail.smtp.auth";
    private static final String MAIL_SMTP_SSL_ENABLE_JMAIL_PROPERTY_NAME = "mail.smtp.ssl.enable";
    @Value("${host:smtp.yandex.ru}")
    private String host;
    @Value("${port:465}")
    private int port;
    @Value("${username:no-reply@tees.org.ua}")
    private String username;
    @Value("${password:SDG6dsASGvd}")
    private String password;
    @Value("${mail.smtp.auth:true}")
    private String mailSmtpAuth;
    @Value("${mail.smtp.ssl.enable:true}")
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
}
