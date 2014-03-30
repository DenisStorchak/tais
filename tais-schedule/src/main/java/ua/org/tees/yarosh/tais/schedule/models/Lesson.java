package ua.org.tees.yarosh.tais.schedule.models;

import ua.org.tees.yarosh.tais.core.common.models.Discipline;
import ua.org.tees.yarosh.tais.core.common.models.Registrant;
import ua.org.tees.yarosh.tais.core.common.models.StudentGroup;
import ua.org.tees.yarosh.tais.schedule.LessonType;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Lesson {
    @OneToOne
    @JoinColumn(name = "disciplineId")
    private Discipline discipline;
    @Id
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    @ManyToOne
    @JoinColumn(name = "studentGroupId")
    private StudentGroup studentGroup;
    @OneToMany
    @JoinColumn(name = "visitersId")
    private List<Registrant> visiters;
    @ManyToOne
    @JoinColumn(name = "teacherId")
    private Registrant teacher;

    @OneToOne
    @JoinColumn(name = "classroom")
    private Classroom classroom;

    @Enumerated
    private LessonType lessonType;

    public Lesson() {
    }

    public Lesson(Lesson lesson) {
        this.discipline = lesson.getDiscipline();
        this.date = lesson.getDate();
        this.studentGroup = lesson.getStudentGroup();
        this.classroom = lesson.getClassroom();
        this.lessonType = lesson.getLessonType();
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

    public StudentGroup getStudentGroup() {
        return studentGroup;
    }

    public void setStudentGroup(StudentGroup studentGroup) {
        this.studentGroup = studentGroup;
    }

    public List<Registrant> getVisiters() {
        return visiters;
    }

    public void setVisiters(List<Registrant> visiters) {
        this.visiters = visiters;
    }

    public Registrant getTeacher() {
        return teacher;
    }

    public void setTeacher(Registrant teacher) {
        this.teacher = teacher;
    }

    public Classroom getClassroom() {
        return classroom;
    }

    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
    }

    public LessonType getLessonType() {
        return lessonType;
    }

    public void setLessonType(LessonType lessonType) {
        this.lessonType = lessonType;
    }
}
