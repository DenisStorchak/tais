package ua.org.tees.yarosh.tais.schedule.api;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ua.org.tees.yarosh.tais.core.common.models.Discipline;

import java.util.List;

public interface DisciplineRepository extends JpaRepository<Discipline, Long> {
    @Query("select d from Discipline d where d.name = :name")
    Discipline findOneByName(@Param("name") String name);

    @Query("select distinct d from Discipline d inner join d.teachers t where t.login = :teacher")
    List<Discipline> findDisciplinesByTeacher(@Param("teacher") String teacher);
}
