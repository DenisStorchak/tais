package ua.org.tees.yarosh.tais.homework.models;

import javax.persistence.*;

@Entity
public class AutoAchievement {
    @Id
    @GeneratedValue
    private Long id;
    @OneToOne
    @JoinColumn(name = "questionSuiteId")
    private QuestionSuite questionSuite;
    private Integer grade;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public QuestionSuite getQuestionSuite() {
        return questionSuite;
    }

    public void setQuestionSuite(QuestionSuite questionSuite) {
        this.questionSuite = questionSuite;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }
}
