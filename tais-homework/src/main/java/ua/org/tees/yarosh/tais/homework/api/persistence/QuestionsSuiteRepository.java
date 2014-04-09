package ua.org.tees.yarosh.tais.homework.api.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.org.tees.yarosh.tais.core.common.models.StudentGroup;
import ua.org.tees.yarosh.tais.homework.models.QuestionsSuite;

import java.util.List;

@Repository
public interface QuestionsSuiteRepository extends JpaRepository<QuestionsSuite, Long> {
    @Query("select q from QuestionsSuite q where q.studentGroup = :studentGroup")
    List<QuestionsSuite> findAllByStudentGroup(@Param("studentGroup") StudentGroup studentGroup);
}
