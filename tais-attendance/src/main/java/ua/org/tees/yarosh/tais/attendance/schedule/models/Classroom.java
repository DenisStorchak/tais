package ua.org.tees.yarosh.tais.attendance.schedule.models;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Classroom {
    @Id
    private String id;

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
