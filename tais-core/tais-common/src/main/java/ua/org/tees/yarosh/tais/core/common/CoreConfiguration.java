package ua.org.tees.yarosh.tais.core.common;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.UrlResource;
import org.springframework.jndi.support.SimpleJndiBeanFactory;
import ua.org.tees.yarosh.tais.core.common.properties.HibernateProperties;
import ua.org.tees.yarosh.tais.core.common.properties.JdbcProperties;
import ua.org.tees.yarosh.tais.core.common.properties.MailProperties;

import java.net.MalformedURLException;
import java.net.URI;

/**
 * @author Timur Yarosh
 *         Date: 09.03.14
 *         Time: 15:51
 */
@Configuration
public class CoreConfiguration {

    @Bean
    public HibernateProperties hibernateProperties() {
        return new HibernateProperties();
    }

    @Bean
    public JdbcProperties jdbcProperties() {
        return new JdbcProperties();
    }

    @Bean
    public MailProperties mailProperties() {
        return new MailProperties();
    }

    @Bean
    public SimpleJndiBeanFactory simpleJndiBeanFactory() {
        return new SimpleJndiBeanFactory();
    }

    @Bean
    public PropertyPlaceholderConfigurer getConfigurer() throws MalformedURLException {
        PropertyPlaceholderConfigurer configurer = new PropertyPlaceholderConfigurer();
        configurer.setLocation(new UrlResource(simpleJndiBeanFactory().getBean("tais.config", URI.class)));
        return configurer;
    }
}
