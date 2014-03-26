package ua.org.tees.yarosh.tais.homework.api.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.org.tees.yarosh.tais.core.common.models.Discipline;
import ua.org.tees.yarosh.tais.core.common.models.Registrant;
import ua.org.tees.yarosh.tais.homework.models.ManualTask;
import ua.org.tees.yarosh.tais.homework.models.ManualTaskReport;

import java.util.List;

@Repository
public interface ManualTaskReportRepository extends JpaRepository<ManualTaskReport, Long> {
    @Query("select m from ManualTaskReport m where m.task = :manualTask and m.owner = :registrant")
    ManualTaskReport findOne(@Param("manualTask") ManualTask manualTask, @Param("registrant") Registrant registrant);

    @Query("select m from ManualTaskReport m where m.task.discipline = :discipline")
    List<ManualTaskReport> findByDiscipline(@Param("discipline") Discipline discipline);
}
