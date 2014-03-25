package ua.org.tees.yarosh.tais.homework;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ua.org.tees.yarosh.tais.core.common.CommonConfiguration;

@Configuration
@Import({CommonConfiguration.class})
@ComponentScan(basePackageClasses = HomeworkConfiguration.class)
public class HomeworkConfiguration {
}
