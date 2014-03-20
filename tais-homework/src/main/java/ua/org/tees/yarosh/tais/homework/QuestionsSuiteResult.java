package ua.org.tees.yarosh.tais.homework;

import ua.org.tees.yarosh.tais.core.common.models.Registrant;
import ua.org.tees.yarosh.tais.homework.models.Answer;
import ua.org.tees.yarosh.tais.homework.models.Question;
import ua.org.tees.yarosh.tais.homework.models.QuestionSuite;

import java.util.Map;

public class QuestionsSuiteResult {
    private QuestionSuite questionSuite;
    private Map<Question, Answer> answers;
    private Registrant owner;

    public QuestionSuite getQuestionSuite() {
        return questionSuite;
    }

    public void setQuestionSuite(QuestionSuite questionSuite) {
        this.questionSuite = questionSuite;
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
