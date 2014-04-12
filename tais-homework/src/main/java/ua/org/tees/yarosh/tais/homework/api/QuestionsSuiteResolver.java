package ua.org.tees.yarosh.tais.homework.api;

import ua.org.tees.yarosh.tais.homework.QuestionsSuiteReport;

public interface QuestionsSuiteResolver {
    /**
     * @return calculated grade
     */
    int resolve(QuestionsSuiteReport report);
}
