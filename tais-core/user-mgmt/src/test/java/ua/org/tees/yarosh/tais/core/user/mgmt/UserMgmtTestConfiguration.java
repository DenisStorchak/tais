package ua.org.tees.yarosh.tais.core.user.mgmt;

import com.jolbox.bonecp.BoneCPDataSource;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@ComponentScan(basePackageClasses = {RegistrationManager.class})
@EnableJpaRepositories("ua.org.tees.yarosh.tais")
public class UserMgmtTestConfiguration {

    @Bean
    public DataSource dataSource() {
        BoneCPDataSource dataSource = new BoneCPDataSource();
        dataSource.setDriverClass("org.h2.Driver");
        dataSource.setJdbcUrl("jdbc:h2:mem:tais_test");
        dataSource.setUsername("tais");
        dataSource.setPassword("tais");
        return dataSource;
    }

    @Bean
    public JpaTransactionManager jpaTransactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return transactionManager;
    }

    @Bean
    JpaTransactionManager transactionManager() {
        return jpaTransactionManager();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource());
        entityManagerFactoryBean.setPackagesToScan("ua.org.tees.yarosh.tais");
        entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        Properties jpaProterties = new Properties();
        jpaProterties.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        jpaProterties.put("hibernate.show_sql", "true");
        entityManagerFactoryBean.setJpaProperties(jpaProterties);
        return entityManagerFactoryBean;
    }
}
