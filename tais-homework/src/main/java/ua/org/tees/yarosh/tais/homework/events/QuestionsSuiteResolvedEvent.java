package ua.org.tees.yarosh.tais.homework.events;

import ua.org.tees.yarosh.tais.homework.QuestionsSuiteReport;

/**
 * @author Timur Yarosh
 *         Date: 12.04.14
 *         Time: 22:12
 */
public class QuestionsSuiteResolvedEvent {

    private QuestionsSuiteReport report;

    public QuestionsSuiteReport getReport() {
        return report;
    }

    public QuestionsSuiteResolvedEvent(QuestionsSuiteReport report) {
        this.report = report;
    }
}
