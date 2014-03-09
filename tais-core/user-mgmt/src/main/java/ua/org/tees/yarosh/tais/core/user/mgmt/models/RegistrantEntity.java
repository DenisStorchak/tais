package ua.org.tees.yarosh.tais.core.user.mgmt.models;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author Timur Yarosh
 *         Date: 09.03.14
 *         Time: 14:30
 */
@Entity
public class RegistrantEntity {
    @Id
    private String login;
    private String password;
    private String name;
    private String patronymic;
    private String surname;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
