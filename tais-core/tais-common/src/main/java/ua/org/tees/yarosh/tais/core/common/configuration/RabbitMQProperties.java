package ua.org.tees.yarosh.tais.core.common.configuration;

import org.springframework.beans.factory.annotation.Value;

public class RabbitMQProperties {
    @Value("${rabbitmq.host}")
    private String host;
    @Value("${rabbitmq.port}")
    private int port;
    @Value("${rabbitmq.password}")
    private String password;
    @Value("${rabbitmq.username}")
    private String username;
    @Value("${rabbitmq.mail.query}")
    private String mailQueue;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMailQueue() {
        return mailQueue;
    }

    public void setMailQueue(String mailQueue) {
        this.mailQueue = mailQueue;
    }
}
