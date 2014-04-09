package ua.org.tees.yarosh.tais.homework.api.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.org.tees.yarosh.tais.core.common.models.StudentGroup;
import ua.org.tees.yarosh.tais.homework.models.ManualTask;

import java.util.List;

@Repository
public interface ManualTaskRepository extends JpaRepository<ManualTask, Long> {
    @Query("select m from ManualTask m where m.studentGroup = :studentGroup")
    List<ManualTask> findByStudentGroup(@Param("studentGroup") StudentGroup studentGroup);
}
