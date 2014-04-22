package ua.org.tees.yarosh.tais.attendance.models;

import ua.org.tees.yarosh.tais.core.common.models.Registrant;
import ua.org.tees.yarosh.tais.schedule.models.Lesson;

import javax.persistence.*;

@Entity
public class Visit {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    @JoinColumn
    private Registrant registrant;
    @ManyToOne
    @JoinColumn
    private Lesson lesson;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Registrant getRegistrant() {
        return registrant;
    }

    public void setRegistrant(Registrant registrant) {
        this.registrant = registrant;
    }

    public Lesson getLesson() {
        return lesson;
    }

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }
}
