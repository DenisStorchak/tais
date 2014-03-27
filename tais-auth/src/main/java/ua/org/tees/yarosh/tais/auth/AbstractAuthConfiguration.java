package ua.org.tees.yarosh.tais.auth;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(AuthConfiguration.class)
public abstract class AbstractAuthConfiguration {
    public abstract UserRepositoryAdapter authDao();
}
