package ua.org.tees.yarosh.tais.homework.api.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.org.tees.yarosh.tais.core.common.models.PersonalTaskHolder;

@Repository
public interface TaskHolderRepository extends JpaRepository<PersonalTaskHolder, Long>{
}
