package ua.org.tees.yarosh.tais.core.user.mgmt.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import ua.org.tees.yarosh.tais.core.common.configuration.BasePersistenceConfiguration;
import ua.org.tees.yarosh.tais.core.user.mgmt.RegistrationManager;

/**
 * @author Timur Yarosh
 *         Date: 09.03.14
 *         Time: 15:25
 */
@Configuration
@Import(BasePersistenceConfiguration.class)
@ComponentScan(basePackageClasses = {RegistrationManager.class})
@EnableJpaRepositories(basePackages = {"ua.org.tees.yarosh.tais.core.user.mgmt.api.persistence"})
public class UserMgmtConfiguration {
}
