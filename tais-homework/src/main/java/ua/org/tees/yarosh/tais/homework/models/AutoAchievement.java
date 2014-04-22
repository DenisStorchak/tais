package ua.org.tees.yarosh.tais.homework.models;

import javax.persistence.*;
import java.util.Date;

@Entity
public class AutoAchievement {
    @Id
    @GeneratedValue
    private Long id;
    @OneToOne
    @JoinColumn(name = "questionSuiteId")
    private QuestionsSuite questionsSuite;
    private Integer grade;
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;

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

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
