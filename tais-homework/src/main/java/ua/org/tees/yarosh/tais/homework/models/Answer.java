package ua.org.tees.yarosh.tais.homework.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Answer {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    private Question question;
    private String text;
    private String payloadPath;
    private Boolean truth;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPayloadPath() {
        return payloadPath;
    }

    public void setPayloadPath(String payloadPath) {
        this.payloadPath = payloadPath;
    }

    public Boolean getTruth() {
        return truth;
    }

    public void setTruth(Boolean truth) {
        this.truth = truth;
    }
}
