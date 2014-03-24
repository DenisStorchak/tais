package ua.org.tees.yarosh.tais.core.common.models;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import ua.org.tees.yarosh.tais.core.common.dto.Role;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author Timur Yarosh
 *         Date: 09.03.14
 *         Time: 15:00
 */
@Entity
public class Registrant implements Serializable {
    @Id
    @NotBlank
    @NotNull
    private String login;
    @Length(min = 6)
    private String password;
    @NotBlank
    @NotNull
    private String name;
    @NotBlank
    @NotNull
    private String patronymic;
    @NotBlank
    @NotNull
    private String surname;
    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @Cascade(CascadeType.ALL)
    private StudentGroup group;
    @Enumerated
    @NotNull
    private Role role;

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

    public StudentGroup getGroup() {
        return group;
    }

    public void setGroup(StudentGroup group) {
        this.group = group;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}