package ua.org.tees.yarosh.tais.homework.api.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.org.tees.yarosh.tais.core.common.models.Registrant;
import ua.org.tees.yarosh.tais.homework.models.ManualTask;
import ua.org.tees.yarosh.tais.homework.models.ManualTaskResult;

@Repository
public interface ManualTaskResultRepository extends JpaRepository<ManualTaskResult, Long> {
    @Query("select m from ManualTaskResult m where m.task = :manualTask and m.owner = :registrant")
    ManualTaskResult findOne(@Param("manualTask") ManualTask manualTask, @Param("registrant") Registrant registrant);
}
