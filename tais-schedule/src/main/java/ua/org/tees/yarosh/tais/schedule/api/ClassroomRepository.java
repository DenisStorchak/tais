package ua.org.tees.yarosh.tais.schedule.api;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.org.tees.yarosh.tais.schedule.models.Classroom;

public interface ClassroomRepository extends JpaRepository<Classroom, String> {
}
