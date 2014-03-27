package ua.org.tees.yarosh.tais.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthConfiguration {
    @Bean
    public AuthManager authManager() {
        return new AuthManager();
    }
}
