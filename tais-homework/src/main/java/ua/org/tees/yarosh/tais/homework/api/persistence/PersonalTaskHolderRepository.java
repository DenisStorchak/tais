package ua.org.tees.yarosh.tais.homework.api.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.org.tees.yarosh.tais.core.common.models.Registrant;
import ua.org.tees.yarosh.tais.homework.models.PersonalTaskHolder;

@Repository
public interface PersonalTaskHolderRepository extends JpaRepository<PersonalTaskHolder, Long> {
    @Query("select p from PersonalTaskHolder p where p.owner = :registrant")
    PersonalTaskHolder findOne(@Param("registrant") Registrant registrant);
}
