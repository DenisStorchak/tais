package ua.org.tees.yarosh.tais.homework;

import com.google.common.eventbus.AsyncEventBus;
import org.springframework.context.annotation.*;
import ua.org.tees.yarosh.tais.core.common.CommonConfiguration;
import ua.org.tees.yarosh.tais.core.user.mgmt.UserMgmtConfiguration;

import static java.util.concurrent.Executors.newCachedThreadPool;

@Configuration
@Import({CommonConfiguration.class, UserMgmtConfiguration.class})
@ComponentScan(basePackageClasses = HomeworkConfiguration.class)
@EnableAspectJAutoProxy
public class HomeworkConfiguration {
    @Bean
    public AsyncEventBus eventBus() {
        return new AsyncEventBus(newCachedThreadPool());
    }
}
