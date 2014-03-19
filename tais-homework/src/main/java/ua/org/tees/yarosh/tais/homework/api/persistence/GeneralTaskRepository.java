package ua.org.tees.yarosh.tais.homework.api.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.org.tees.yarosh.tais.core.common.models.GroupTask;

public interface GeneralTaskRepository extends JpaRepository<GroupTask, Long> {
}
