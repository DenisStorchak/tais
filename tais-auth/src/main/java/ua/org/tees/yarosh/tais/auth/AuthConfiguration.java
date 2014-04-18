package ua.org.tees.yarosh.tais.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ua.org.tees.yarosh.tais.core.common.CommonConfiguration;

@Import(CommonConfiguration.class)
@Configuration
public class AuthConfiguration {
    @Bean
    public AuthManager authManager() {
        return new AuthManager();
    }
}
