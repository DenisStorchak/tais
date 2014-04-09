package ua.org.tees.yarosh.tais.homework.models;

import ua.org.tees.yarosh.tais.core.common.models.Registrant;

import javax.persistence.*;
import java.util.List;

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
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "manualTasks")
    private List<ManualTask> manualTaskList;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "questionsSuites")
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
