package ua.org.tees.yarosh.tais.homework;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import ua.org.tees.yarosh.tais.core.common.CommonConfiguration;
import ua.org.tees.yarosh.tais.core.user.mgmt.UserMgmtConfiguration;

@Configuration
@Import({CommonConfiguration.class, UserMgmtConfiguration.class})
@ComponentScan(basePackageClasses = HomeworkConfiguration.class)
@EnableAspectJAutoProxy
public class HomeworkConfiguration {
}
