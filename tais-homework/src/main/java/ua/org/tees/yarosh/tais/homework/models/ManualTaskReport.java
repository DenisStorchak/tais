package ua.org.tees.yarosh.tais.homework.models;

import ua.org.tees.yarosh.tais.core.common.models.Registrant;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = "taskId")})
public class ManualTaskReport {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    @JoinColumn(name = "taskId")
    private ManualTask task;
    private String filePath;
    @ManyToOne
    @JoinColumn(name = "ownerId")
    private Registrant owner;
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ManualTask getTask() {
        return task;
    }

    public void setTask(ManualTask task) {
        this.task = task;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Registrant getOwner() {
        return owner;
    }

    public void setOwner(Registrant owner) {
        this.owner = owner;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
