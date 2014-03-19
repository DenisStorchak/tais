package ua.org.tees.yarosh.tais.core.user.mgmt.api.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.org.tees.yarosh.tais.core.common.models.Discipline;

@Repository
public interface DisciplineRepository extends JpaRepository<Discipline, Long> {
}
