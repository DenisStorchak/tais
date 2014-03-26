package ua.org.tees.yarosh.tais.core.common.models;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.List;

/**
 * @author Timur Yarosh
 *         Date: 09.03.14
 *         Time: 22:20
 */
@Entity
public class StudentGroup implements Serializable {
    @Id
    private String id;
    @Cascade(CascadeType.ALL)
    @OneToMany(fetch = FetchType.EAGER)
    private List<Registrant> students;

    public StudentGroup() {
    }

    public StudentGroup(String id, List<Registrant> students) {
        this.id = id;
        this.students = students;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Registrant> getStudents() {
        return students;
    }

    public void setStudents(List<Registrant> students) {
        this.students = students;
    }
}
