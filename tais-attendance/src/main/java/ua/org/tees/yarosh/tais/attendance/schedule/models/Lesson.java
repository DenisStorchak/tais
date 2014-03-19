package ua.org.tees.yarosh.tais.attendance.schedule.models;

import ua.org.tees.yarosh.tais.core.common.models.Discipline;
import ua.org.tees.yarosh.tais.core.common.models.StudentGroup;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Lesson {
    @Id
    @GeneratedValue
    private Long id;
    @OneToOne
    @JoinColumn(name = "disciplineId")
    private Discipline discipline;
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    @ManyToOne
    @JoinColumn(name = "studentGroupId")
    private StudentGroup studentGroup;

    public Lesson() {
    }

    public Lesson(Lesson lesson) {
        this.id = lesson.getId();
        this.discipline = lesson.getDiscipline();
        this.date = lesson.getDate();
        this.studentGroup = lesson.getStudentGroup();

    }

    public Discipline getDiscipline() {
        return discipline;
    }

    public void setDiscipline(Discipline discipline) {
        this.discipline = discipline;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StudentGroup getStudentGroup() {
        return studentGroup;
    }

    public void setStudentGroup(StudentGroup studentGroup) {
        this.studentGroup = studentGroup;
    }
}
