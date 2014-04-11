package ua.org.tees.yarosh.tais.schedule.api;

import ua.org.tees.yarosh.tais.core.common.models.Discipline;

import java.util.List;

public interface DisciplineService {
    List<Discipline> findAllDisciplines();

    Discipline createDiscipline(Discipline discipline);

    Discipline findDiscipline(Long id);

    Discipline findDisciplineByName(String name);

    List<Discipline> findDisciplinesByTeacher(String login);
}
