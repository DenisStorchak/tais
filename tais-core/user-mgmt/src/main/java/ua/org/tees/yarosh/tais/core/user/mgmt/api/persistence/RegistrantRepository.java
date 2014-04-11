package ua.org.tees.yarosh.tais.core.user.mgmt.api.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.org.tees.yarosh.tais.core.common.models.Registrant;

import java.util.List;

/**
 * @author Timur Yarosh
 *         Date: 09.03.14
 *         Time: 14:38
 */
@Repository
public interface RegistrantRepository extends JpaRepository<Registrant, String> {
    @Query("select r from Registrant r where r.role = :role")
    public List<Registrant> findAllByRole(@Param("role") String role);
}
