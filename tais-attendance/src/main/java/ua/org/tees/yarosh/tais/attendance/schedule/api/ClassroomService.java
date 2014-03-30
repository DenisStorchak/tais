package ua.org.tees.yarosh.tais.attendance.schedule.api;

import ua.org.tees.yarosh.tais.attendance.schedule.models.Classroom;

import java.util.List;

public interface ClassroomService {
    List<Classroom> findAllClassrooms();

    Classroom createClassroom(Classroom classroom);

    Classroom findClassroom(String id);
}
