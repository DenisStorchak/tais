package ua.org.tees.yarosh.tais.homework.events;

import ua.org.tees.yarosh.tais.homework.models.QuestionsSuite;

/**
 * @author Timur Yarosh
 *         Date: 12.04.14
 *         Time: 23:04
 */
public class QuestionsSuiteDisabledEvent {

    private QuestionsSuite suite;

    public QuestionsSuite getSuite() {
        return suite;
    }

    public QuestionsSuiteDisabledEvent(QuestionsSuite suite) {
        this.suite = suite;
    }
}
