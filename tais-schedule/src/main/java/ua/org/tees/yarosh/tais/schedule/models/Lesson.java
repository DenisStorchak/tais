package ua.org.tees.yarosh.tais.schedule.models;

import ua.org.tees.yarosh.tais.core.common.models.Discipline;
import ua.org.tees.yarosh.tais.core.common.models.Registrant;
import ua.org.tees.yarosh.tais.core.common.models.StudentGroup;
import ua.org.tees.yarosh.tais.schedule.LessonType;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Lesson {
    @Id
    @GeneratedValue
    private long id;
    @OneToOne
    @JoinColumn(name = "disciplineId")
    private Discipline discipline;
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    @ManyToOne
    @JoinColumn(name = "studentGroupId")
    private StudentGroup studentGroup;
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
        this.teacher = lesson.getTeacher();
        this.classroom = lesson.getClassroom();
        this.lessonType = lesson.getLessonType();
        // don't copy visitors to new lesson
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
