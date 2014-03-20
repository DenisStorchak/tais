package ua.org.tees.yarosh.tais.core.common.models;

import ua.org.tees.yarosh.tais.core.common.dto.Position;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Timur Yarosh
 *         Date: 09.03.14
 *         Time: 15:00
 */
@Entity
public class Registrant implements Serializable {
    @Id
    private String login;
    private String password;
    private String name;
    private String patronymic;
    private String surname;
    @ManyToOne
    private StudentGroup group;
    @OneToOne
    private PersonalTaskHolder personalTaskHolder;
    @Enumerated
    private Position position;

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

    public PersonalTaskHolder getPersonalTaskHolder() {
        return personalTaskHolder;
    }

    public void setPersonalTaskHolder(PersonalTaskHolder personalTaskHolder) {
        this.personalTaskHolder = personalTaskHolder;
    }

    public StudentGroup getGroup() {
        return group;
    }

    public void setGroup(StudentGroup group) {
        this.group = group;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}