package ua.org.tees.yarosh.tais.homework.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ua.org.tees.yarosh.tais.core.common.CommonConfiguration;

@Configuration
@Import({CommonConfiguration.class, CachingConfiguration.class})
@ComponentScan(basePackageClasses = HomeworkConfiguration.class)
public class HomeworkConfiguration {
}
