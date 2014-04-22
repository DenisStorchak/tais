package ua.org.tees.yarosh.tais.homework.models;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.FetchType.EAGER;

@Entity
public class Question {
    @Id
    @GeneratedValue
    private Long id;
    private String description;
    private String payloadPath;
    @Cascade(CascadeType.ALL)
    @OneToMany(fetch = EAGER)
    private List<Answer> answers;
    @ManyToOne
    @JoinColumn(name = "suite_id")
    private QuestionsSuite questionsSuite;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPayloadPath() {
        return payloadPath;
    }

    public void setPayloadPath(String payloadPath) {
        this.payloadPath = payloadPath;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }
}
