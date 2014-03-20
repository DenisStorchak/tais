package ua.org.tees.yarosh.tais.homework.api.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.org.tees.yarosh.tais.homework.models.QuestionsSuite;

@Repository
public interface QuestionsSuiteRepository extends JpaRepository<QuestionsSuite, Long> {
}
