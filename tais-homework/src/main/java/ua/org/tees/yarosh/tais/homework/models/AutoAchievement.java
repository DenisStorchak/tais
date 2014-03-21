package ua.org.tees.yarosh.tais.homework.models;

import javax.persistence.*;

@Entity
public class AutoAchievement {
    @Id
    @GeneratedValue
    private Long id;
    @OneToOne
    @JoinColumn(name = "questionSuiteId")
    private QuestionsSuite questionsSuite;
    private Integer grade;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public QuestionsSuite getQuestionsSuite() {
        return questionsSuite;
    }

    public void setQuestionsSuite(QuestionsSuite questionsSuite) {
        this.questionsSuite = questionsSuite;
    }
}
