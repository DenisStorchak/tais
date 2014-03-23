package ua.org.tees.yarosh.tais.core.user.mgmt.api.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.org.tees.yarosh.tais.core.common.models.Registrant;

/**
 * @author Timur Yarosh
 *         Date: 09.03.14
 *         Time: 14:38
 */
public interface RegistrantRepository extends JpaRepository<Registrant, String> {
}
