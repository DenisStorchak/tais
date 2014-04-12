package ua.org.tees.yarosh.tais.homework.events;

import ua.org.tees.yarosh.tais.homework.models.QuestionsSuite;

/**
 * @author Timur Yarosh
 *         Date: 12.04.14
 *         Time: 22:11
 */
public class QuestionsSuiteRegisteredEvent {

    private QuestionsSuite suite;

    public QuestionsSuite getSuite() {
        return suite;
    }

    public QuestionsSuiteRegisteredEvent(QuestionsSuite suite) {
        this.suite = suite;
    }
}
