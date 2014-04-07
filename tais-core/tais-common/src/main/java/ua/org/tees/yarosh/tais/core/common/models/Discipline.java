package ua.org.tees.yarosh.tais.core.common.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author Timur Yarosh
 *         Date: 09.03.14
 *         Time: 22:08
 */
@Entity
public class Discipline {
    @Id
    @GeneratedValue
    private Long id;
    private String name;

    public Discipline(String discipline) {
        name = discipline;
    }

    public Discipline() {
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (obj.getClass() != getClass()) return false;

        Discipline that = (Discipline) obj;

        return this.getName().equals(that.getName());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
