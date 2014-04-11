package ua.org.tees.yarosh.tais.homework.models;

import ua.org.tees.yarosh.tais.core.common.models.Discipline;
import ua.org.tees.yarosh.tais.core.common.models.Registrant;
import ua.org.tees.yarosh.tais.core.common.models.StudentGroup;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Timur Yarosh
 *         Date: 09.03.14
 *         Time: 22:12
 */
@Entity
public class ManualTask {
    @Id
    @GeneratedValue
    private Long id;
    private String description;
    private String theme;
    @ManyToOne
    @JoinColumn(name = "disciplineId")
    private Discipline discipline;
    @ManyToOne
    @JoinColumn(name = "studentGroupId")
    private StudentGroup studentGroup;
    private Boolean enabled;
    private String payloadPath;
    @Temporal(TemporalType.DATE)
    private Date deadline;
    @JoinColumn(name = "examinerId")
    @ManyToOne
    private Registrant examiner;

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

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public Registrant getExaminer() {
        return examiner;
    }

    public void setExaminer(Registrant examiner) {
        this.examiner = examiner;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }
}
