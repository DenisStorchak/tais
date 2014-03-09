package ua.org.tees.yarosh.tais.user.comm.configuration;

import org.springframework.stereotype.Repository;

/**
 * @author Timur Yarosh
 *         Date: 09.03.14
 *         Time: 23:04
 */
@Repository
public class MailSenderLoader {
    private String host;
    private int port;
    private String username;
    private String password;
    private String mailSmtpAuth;
    private String mailSmtpSslEnable;

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMailSmtpAuth() {
        return mailSmtpAuth;
    }

    public void setMailSmtpAuth(String mailSmtpAuth) {
        this.mailSmtpAuth = mailSmtpAuth;
    }

    public String getMailSmtpSslEnable() {
        return mailSmtpSslEnable;
    }

    public void setMailSmtpSslEnable(String mailSmtpSslEnable) {
        this.mailSmtpSslEnable = mailSmtpSslEnable;
    }
}
