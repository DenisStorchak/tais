package ua.org.tees.yarosh.tais.ui.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import ua.org.tees.yarosh.tais.auth.AuthManager;
import ua.org.tees.yarosh.tais.auth.UserDetails;
import ua.org.tees.yarosh.tais.auth.UserRepositoryAdapter;
import ua.org.tees.yarosh.tais.core.common.properties.DefaultUserProperties;
import ua.org.tees.yarosh.tais.core.user.mgmt.DatabaseDaoAdapter;

import javax.annotation.PostConstruct;

/**
 * @author Timur Yarosh
 *         Date: 27.03.14
 *         Time: 20:50
 */
@Service
@Scope("singleton")
public class AuthConfigurer {

    @Autowired
    private DatabaseDaoAdapter databaseDaoAdapter;
    @Autowired
    private DefaultUserProperties defaultUserProperties;

    @PostConstruct
    public void addDataBaseAdapter() {
        AuthManager.addDao(databaseDaoAdapter);
    }

    @PostConstruct
    public void addInMemoryAdapter() {
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
