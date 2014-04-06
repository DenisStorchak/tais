package ua.org.tees.yarosh.tais.core.common.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * @author Timur Yarosh
 *         Date: 09.03.14
 *         Time: 22:20
 */
@Entity
public class StudentGroup implements Serializable {
    @Id
    private String id;

    public StudentGroup() {
    }

    public StudentGroup(String value) {
        id = value;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null) return false;
        if (obj.getClass() != getClass()) return false;

        StudentGroup that = (StudentGroup) obj;
        return that.getId().equals(this.getId());
    }

    @Override
    public String toString() {
        return id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
