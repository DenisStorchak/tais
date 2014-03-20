package ua.org.tees.yarosh.tais.homework.models;

import ua.org.tees.yarosh.tais.core.common.models.PersonalTask;
import ua.org.tees.yarosh.tais.core.common.models.Registrant;

import javax.persistence.*;

@Entity
public class ManualAchievement {
    @Id
    @GeneratedValue
    private Long id;
    @OneToOne
    @JoinColumn(name = "personalTaskId")
    private PersonalTask personalTask;
    private Integer grade;
    @ManyToOne
    @JoinColumn(name = "examinerId")
    private Registrant examiner;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PersonalTask getPersonalTask() {
        return personalTask;
    }

    public void setPersonalTask(PersonalTask personalTask) {
        this.personalTask = personalTask;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public Registrant getExaminer() {
        return examiner;
    }

    public void setExaminer(Registrant examiner) {
        this.examiner = examiner;
    }
}
