package ua.org.tees.yarosh.tais.attendance;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@ComponentScan(basePackageClasses = AttendanceConfiguration.class)
@EnableJpaRepositories(basePackages = "ua.org.tees.yarosh.tais.attendance.schedule.api")
@EnableWebMvc
public class AttendanceConfiguration {
}
