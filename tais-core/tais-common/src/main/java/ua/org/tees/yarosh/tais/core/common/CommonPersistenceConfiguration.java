package ua.org.tees.yarosh.tais.core.common;

import com.jolbox.bonecp.BoneCPDataSource;
import org.hibernate.ejb.HibernatePersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import ua.org.tees.yarosh.tais.core.common.properties.HibernateProperties;
import ua.org.tees.yarosh.tais.core.common.properties.JdbcProperties;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * @author Timur Yarosh
 *         Date: 20.03.14
 *         Time: 23:52
 */
@Configuration
@Import(ExternalConfiguration.class)
@EnableJpaRepositories("ua.org.tees.yarosh.tais")
public class CommonPersistenceConfiguration {

    private JdbcProperties jdbcProperties;
    private HibernateProperties hibernateProperties;

    @Autowired
    public void setJdbcProperties(JdbcProperties jdbcProperties) {
        this.jdbcProperties = jdbcProperties;
    }

    @Autowired
    public void setHibernateProperties(HibernateProperties hibernateProperties) {
        this.hibernateProperties = hibernateProperties;
    }

    @Bean
    public DataSource dataSource() {
        BoneCPDataSource dataSource = new BoneCPDataSource();
        dataSource.setDriverClass(jdbcProperties.getJdbcDriverClass());
        dataSource.setJdbcUrl(jdbcProperties.getJdbcUrl());
        dataSource.setUsername(jdbcProperties.getJdbcUsername());
        dataSource.setPassword(jdbcProperties.getJdbcPassword());
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
        entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistence.class);
        Properties jpaProterties = new Properties();
        jpaProterties.put("hibernate.dialect", hibernateProperties.getHibernateDialect());
        jpaProterties.put("hibernate.hbm2ddl.auto", hibernateProperties.getHibernateHbm2ddlAuto());
        jpaProterties.put("hibernate.show_sql", hibernateProperties.getHibernateShowSql());
        entityManagerFactoryBean.setJpaProperties(jpaProterties);
        return entityManagerFactoryBean;
    }
}
