package ua.org.tees.yarosh.tais.core.common.properties;

import org.springframework.beans.factory.annotation.Value;

/**
 * @author Timur Yarosh
 *         Date: 27.03.14
 *         Time: 20:40
 */
public class DefaultUserProperties {
    @Value("${user.login}")
    private String login;
    @Value("${user.password}")
    private String password;
    @Value("${user.role}")
    private String role;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
