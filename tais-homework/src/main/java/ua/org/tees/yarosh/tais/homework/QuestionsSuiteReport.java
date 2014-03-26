package ua.org.tees.yarosh.tais.homework;

import ua.org.tees.yarosh.tais.core.common.models.Registrant;
import ua.org.tees.yarosh.tais.homework.models.Answer;
import ua.org.tees.yarosh.tais.homework.models.Question;
import ua.org.tees.yarosh.tais.homework.models.QuestionsSuite;

import java.util.Map;

public class QuestionsSuiteReport {
    private QuestionsSuite questionsSuite;
    private Map<Question, Answer> answers;
    private Registrant owner;

    public QuestionsSuite getQuestionsSuite() {
        return questionsSuite;
    }

    public void setQuestionsSuite(QuestionsSuite questionsSuite) {
        this.questionsSuite = questionsSuite;
    }

    public Map<Question, Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(Map<Question, Answer> answers) {
        this.answers = answers;
    }

    public Registrant getOwner() {
        return owner;
    }

    public void setOwner(Registrant owner) {
        this.owner = owner;
    }
}
