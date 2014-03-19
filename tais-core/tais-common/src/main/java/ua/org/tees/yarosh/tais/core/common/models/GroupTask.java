package ua.org.tees.yarosh.tais.core.common.models;

import javax.persistence.*;

/**
 * @author Timur Yarosh
 *         Date: 09.03.14
 *         Time: 22:12
 */
@Entity
public class GroupTask {
    @Id
    @GeneratedValue
    private Long id;
    private String description;
    @ManyToOne
    @JoinColumn(name = "disciplineId")
    private Discipline discipline;
    @ManyToOne
    @JoinColumn(name = "studentGroupId")
    private StudentGroup studentGroup;
    private Boolean enabled;
    private String payloadPath;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Discipline getDiscipline() {
        return discipline;
    }

    public void setDiscipline(Discipline discipline) {
        this.discipline = discipline;
    }

    public StudentGroup getStudentGroup() {
        return studentGroup;
    }

    public void setStudentGroup(StudentGroup studentGroup) {
        this.studentGroup = studentGroup;
    }

    public Boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getPayloadPath() {
        return payloadPath;
    }

    public void setPayloadPath(String payloadPath) {
        this.payloadPath = payloadPath;
    }
}
