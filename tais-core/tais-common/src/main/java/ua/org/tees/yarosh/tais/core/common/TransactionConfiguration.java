package ua.org.tees.yarosh.tais.core.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate3.HibernateTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * @author Timur Yarosh
 *         Date: 23.03.14
 *         Time: 2:45
 */
@Configuration
public class TransactionConfiguration {
    @Bean
    public PlatformTransactionManager transactionManager() {
        return new HibernateTransactionManager();
    }
}
