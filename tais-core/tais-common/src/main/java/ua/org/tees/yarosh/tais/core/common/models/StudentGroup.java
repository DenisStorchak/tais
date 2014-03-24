package ua.org.tees.yarosh.tais.core.common.models;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

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
    @Cascade(CascadeType.ALL)
    @OneToMany
    private List<Registrant> students;

    public StudentGroup() {
    }

    public StudentGroup(Integer integer, List<Registrant> students) {
        id = integer;
        this.students = students;
    }

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
