package ua.org.tees.yarosh.tais.core.user.mgmt.api.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.org.tees.yarosh.tais.core.user.mgmt.models.RegistrantEntity;

/**
 * @author Timur Yarosh
 *         Date: 09.03.14
 *         Time: 14:38
 */
@Repository
public interface RegistrantRepository extends JpaRepository<RegistrantEntity, String> {
}
