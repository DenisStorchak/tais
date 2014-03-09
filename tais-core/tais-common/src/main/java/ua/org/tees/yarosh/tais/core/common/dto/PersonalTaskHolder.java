package ua.org.tees.yarosh.tais.core.common.dto;

import java.util.List;

/**
 * @author Timur Yarosh
 *         Date: 09.03.14
 *         Time: 22:14
 */
public class PersonalTaskHolder {
    private Registrant owner;
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
}
