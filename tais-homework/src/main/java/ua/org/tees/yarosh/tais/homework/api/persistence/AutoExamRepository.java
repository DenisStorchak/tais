package ua.org.tees.yarosh.tais.homework.api.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.org.tees.yarosh.tais.core.common.models.Discipline;
import ua.org.tees.yarosh.tais.core.common.models.StudentGroup;
import ua.org.tees.yarosh.tais.homework.models.QuestionsSuite;

import java.util.List;

@Repository
public interface AutoExamRepository extends JpaRepository<QuestionsSuite, Long> {
    List<QuestionsSuite> findQuestionSuite(Discipline discipline, StudentGroup studentGroup);
}
