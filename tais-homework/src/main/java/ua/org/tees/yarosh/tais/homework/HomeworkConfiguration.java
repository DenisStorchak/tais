package ua.org.tees.yarosh.tais.homework;

import com.google.common.eventbus.EventBus;
import org.springframework.context.annotation.*;
import ua.org.tees.yarosh.tais.core.common.CommonConfiguration;
import ua.org.tees.yarosh.tais.core.user.mgmt.UserMgmtConfiguration;

@Configuration
@Import({CommonConfiguration.class, UserMgmtConfiguration.class})
@ComponentScan(basePackageClasses = HomeworkConfiguration.class)
@EnableAspectJAutoProxy
public class HomeworkConfiguration {
    @Bean
    public EventBus eventBus() {
        return new EventBus();
    }
}
