package ua.org.tees.yarosh.tais.schedule.models;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Classroom {
    @Id
    private String id;

    public Classroom() {
    }

    public Classroom(String classroom) {
        this.id = classroom;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return id;
    }
}
