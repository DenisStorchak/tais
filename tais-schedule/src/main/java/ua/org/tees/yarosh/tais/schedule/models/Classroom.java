package ua.org.tees.yarosh.tais.schedule.models;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Classroom {
    @Id
    private String id;

    public Classroom() {
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj.getClass() != this.getClass()) return false;
        if (obj == this) return true;

        Classroom that = (Classroom) obj;

        return this.id.equals(that.id);
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
