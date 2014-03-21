package ua.org.tees.yarosh.tais.core.common.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

/**
 * @author Timur Yarosh
 *         Date: 09.03.14
 *         Time: 22:20
 */
@Entity
public class StudentGroup {
    @Id
    private Integer id;
    @OneToMany
    private List<Registrant> students;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Registrant> getStudents() {
        return students;
    }

    public void setStudents(List<Registrant> students) {
        this.students = students;
    }
}
