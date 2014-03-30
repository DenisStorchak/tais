package ua.org.tees.yarosh.tais.schedule.api;

import ua.org.tees.yarosh.tais.core.common.models.Discipline;
import ua.org.tees.yarosh.tais.core.common.models.Registrant;

import java.util.List;

public interface TeacherService {
    List<Discipline> findDisciplinesByTeacher(Registrant teacher);
}
