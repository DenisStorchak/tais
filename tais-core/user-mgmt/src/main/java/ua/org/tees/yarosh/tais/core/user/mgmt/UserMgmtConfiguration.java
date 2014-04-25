package ua.org.tees.yarosh.tais.core.user.mgmt;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ua.org.tees.yarosh.tais.core.common.CommonConfiguration;

/**
 * @author Timur Yarosh
 *         Date: 09.03.14
 *         Time: 15:25
 */
@Configuration
@Import({CommonConfiguration.class})
@ComponentScan(basePackageClasses = {RegistrationManager.class})
public class UserMgmtConfiguration {
}
