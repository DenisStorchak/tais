package ua.org.tees.yarosh.tais.core.common;

import com.google.common.eventbus.AsyncEventBus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.concurrent.Executors;

/**
 * @author Timur Yarosh
 *         Date: 09.03.14
 *         Time: 15:51
 */
@Configuration
@Import({CommonPersistenceConfiguration.class, CachingConfiguration.class})
public class CommonConfiguration {

    @Bean
    public AsyncEventBus asyncEventBus() {
        return new AsyncEventBus(Executors.newCachedThreadPool());
    }
}
