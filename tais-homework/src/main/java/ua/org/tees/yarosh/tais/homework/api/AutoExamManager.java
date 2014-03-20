package ua.org.tees.yarosh.tais.homework.api;

import ua.org.tees.yarosh.tais.core.common.models.Discipline;
import ua.org.tees.yarosh.tais.core.common.models.StudentGroup;
import ua.org.tees.yarosh.tais.homework.models.QuestionSuite;

import java.util.List;

public interface AutoExamManager {
    void addQuestionSuite(QuestionSuite questionSuite);

    List<QuestionSuite> findQuestionSuite(Discipline discipline, StudentGroup studentGroup);

    void updateQuestionSuite(QuestionSuite questionSuite);

    void deleteQuestionSuite(QuestionSuite questionSuite);
}
