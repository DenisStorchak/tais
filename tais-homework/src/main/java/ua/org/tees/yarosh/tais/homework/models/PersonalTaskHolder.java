package ua.org.tees.yarosh.tais.homework.models;

import org.hibernate.annotations.Cascade;
import ua.org.tees.yarosh.tais.core.common.models.Registrant;

import javax.persistence.*;
import java.util.List;

import static org.hibernate.annotations.CascadeType.ALL;

/**
 * @author Timur Yarosh
 *         Date: 09.03.14
 *         Time: 22:14
 */
@Entity
public class PersonalTaskHolder {
    @Id
    @GeneratedValue
    private Long id;
    @OneToOne
    @JoinColumn(name = "ownerId")
    private Registrant owner;
    @Cascade(ALL)
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable
    private List<ManualTask> manualTaskList;
    @Cascade(ALL)
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable
    private List<QuestionsSuite> questionsSuiteList;

    public Registrant getOwner() {
        return owner;
    }

    public void setOwner(Registrant owner) {
        this.owner = owner;
    }

    public List<ManualTask> getManualTaskList() {
        return manualTaskList;
    }

    public void setManualTaskList(List<ManualTask> manualTaskList) {
        this.manualTaskList = manualTaskList;
    }

    public List<QuestionsSuite> getQuestionsSuiteList() {
        return questionsSuiteList;
    }

    public void setQuestionsSuiteList(List<QuestionsSuite> questionsSuiteList) {
        this.questionsSuiteList = questionsSuiteList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
