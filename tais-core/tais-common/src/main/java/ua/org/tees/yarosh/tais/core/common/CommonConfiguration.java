package ua.org.tees.yarosh.tais.core.common;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author Timur Yarosh
 *         Date: 09.03.14
 *         Time: 15:51
 */
@Configuration
@Import({CommonPersistenceConfiguration.class, CachingConfiguration.class})
public class CommonConfiguration {

}
