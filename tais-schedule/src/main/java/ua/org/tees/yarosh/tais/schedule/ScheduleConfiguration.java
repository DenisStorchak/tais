package ua.org.tees.yarosh.tais.schedule;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import ua.org.tees.yarosh.tais.core.common.CommonConfiguration;

/**
 * @author Timur Yarosh
 *         Date: 30.03.14
 *         Time: 12:51
 */
@Configuration
@Import(CommonConfiguration.class)
@ComponentScan(basePackages = "ua.org.tees.yarosh.tais.schedule")
@EnableJpaRepositories(basePackages = "ua.org.tees.yarosh.tais.attendance")
public class ScheduleConfiguration {
}
