package ua.org.tees.yarosh.tais.homework;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan(basePackageClasses = HomeworkConfiguration.class)
@EnableJpaRepositories(basePackages = "ua.org.tees.yarosh.tais.homework.api.persistence")
public class HomeworkConfiguration {
}
