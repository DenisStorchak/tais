package ua.org.tees.yarosh.tais.core.common.models;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Timur Yarosh
 *         Date: 09.03.14
 *         Time: 22:17
 */
@Entity
public class PersonalTask {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    @JoinColumn(name = "groupTaskId")
    private GroupTask groupTask;
    private String grade;
    private String payloadPath;
    @Temporal(TemporalType.DATE)
    private Date deadline;

    public PersonalTask() {
    }

    public PersonalTask(GroupTask task) {
        this.groupTask = task;

    }

    public GroupTask getGroupTask() {
        return groupTask;
    }

    public void setGroupTask(GroupTask groupTask) {
        this.groupTask = groupTask;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPayloadPath() {
        return payloadPath;
    }

    public void setPayloadPath(String payloadPath) {
        this.payloadPath = payloadPath;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }
}
