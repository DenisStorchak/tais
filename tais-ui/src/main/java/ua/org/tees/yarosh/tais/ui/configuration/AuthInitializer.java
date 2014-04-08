package ua.org.tees.yarosh.tais.ui.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.org.tees.yarosh.tais.auth.AuthManager;
import ua.org.tees.yarosh.tais.auth.UserDetails;
import ua.org.tees.yarosh.tais.auth.UserRepositoryAdapter;
import ua.org.tees.yarosh.tais.core.common.properties.DefaultUserProperties;
import ua.org.tees.yarosh.tais.core.user.mgmt.DatabaseDaoAdapter;

/**
 * @author Timur Yarosh
 *         Date: 27.03.14
 *         Time: 20:50
 */
@Component
public class AuthInitializer implements InitializingBean {

    private static final Logger log = LoggerFactory.getLogger(AuthInitializer.class);
    @Autowired
    private DatabaseDaoAdapter databaseDaoAdapter;
    @Autowired
    private DefaultUserProperties defaultUserProperties;

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("AuthManager initializing...");
        addDataBaseAdapter();
        addInMemoryAdapter();
        AuthManager.enable();
    }

    private void addDataBaseAdapter() {
        AuthManager.addDao(databaseDaoAdapter);
    }

    private void addInMemoryAdapter() {
        AuthManager.setAcceptsEmptyStrings(false);
        AuthManager.addDao(new UserRepositoryAdapter() {
            @Override
            public UserDetails getUserDetails(String login) {
                UserDetails userDetails = null;
                if (login.equals(defaultUserProperties.getLogin())) {
                    userDetails = new UserDetails();
                    userDetails.setUsername(defaultUserProperties.getLogin());
                    userDetails.setPassword(defaultUserProperties.getPassword());
                    userDetails.setRole(defaultUserProperties.getRole());
                }
                return userDetails;
            }

            @Override
            public boolean contains(String login) {
                return defaultUserProperties.getLogin().equals(login);
            }
        });
    }
}
