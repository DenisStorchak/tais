package ua.org.tees.yarosh.tais.attendance;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import ua.org.tees.yarosh.tais.core.common.CommonConfiguration;

@Configuration
@Import(CommonConfiguration.class)
@ComponentScan(basePackageClasses = AttendanceConfiguration.class)
@EnableJpaRepositories(basePackages = "ua.org.tees.yarosh.tais.attendance")
@EnableWebMvc
public class AttendanceConfiguration {
}
