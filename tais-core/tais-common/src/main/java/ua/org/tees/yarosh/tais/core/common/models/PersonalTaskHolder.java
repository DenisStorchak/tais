package ua.org.tees.yarosh.tais.core.common.models;

import javax.persistence.*;
import java.util.List;

/**
 * @author Timur Yarosh
 *         Date: 09.03.14
 *         Time: 22:14
 */
@Entity
public class PersonalTaskHolder {
    @Id
    @GeneratedValue
    private Long id;
    @OneToOne
    private Registrant owner;
    @OneToMany
    @JoinColumn(name = "tasks")
    private List<PersonalTask> personalTaskList;

    public Registrant getOwner() {
        return owner;
    }

    public void setOwner(Registrant owner) {
        this.owner = owner;
    }

    public List<PersonalTask> getPersonalTaskList() {
        return personalTaskList;
    }

    public void setPersonalTaskList(List<PersonalTask> personalTaskList) {
        this.personalTaskList = personalTaskList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
