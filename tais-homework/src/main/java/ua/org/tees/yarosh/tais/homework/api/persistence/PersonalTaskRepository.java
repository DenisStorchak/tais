package ua.org.tees.yarosh.tais.homework.api.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.org.tees.yarosh.tais.core.common.models.GroupTask;
import ua.org.tees.yarosh.tais.core.common.models.PersonalTask;

@Repository
public interface PersonalTaskRepository extends JpaRepository<PersonalTask, Long> {
    @Query("select p from PersonalTask p where p.groupTask = :groupTask")
    public PersonalTask findByGroupTask(@Param("groupTask") GroupTask task);
}
