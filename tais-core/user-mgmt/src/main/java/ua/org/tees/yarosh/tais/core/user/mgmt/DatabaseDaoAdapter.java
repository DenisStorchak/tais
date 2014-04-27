package ua.org.tees.yarosh.tais.core.user.mgmt;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.org.tees.yarosh.tais.auth.UserDetails;
import ua.org.tees.yarosh.tais.auth.UserRepositoryAdapter;
import ua.org.tees.yarosh.tais.core.common.exceptions.RegistrantNotFoundException;
import ua.org.tees.yarosh.tais.core.common.models.Registrant;
import ua.org.tees.yarosh.tais.core.user.mgmt.api.service.RegistrantService;

/**
 * @author Timur Yarosh
 *         Date: 27.03.14
 *         Time: 20:36
 */
@Service
public class DatabaseDaoAdapter implements UserRepositoryAdapter {

    public static final Logger log = LoggerFactory.getLogger(DatabaseDaoAdapter.class);
    private RegistrantService registrantService;

    @Autowired
    public void setRegistrantService(RegistrantService registrantService) {
        this.registrantService = registrantService;
    }

    @Override
    public UserDetails getUserDetails(String login) {
        UserDetails userDetails = null;
        try {
            Registrant registration = registrantService.getRegistration(login);
            userDetails = new UserDetails();
            userDetails.setUsername(registration.getLogin());
            userDetails.setPassword(registration.getPassword());
            userDetails.setRole(registration.getRole());
        } catch (RegistrantNotFoundException e) {
            log.warn("No such login [{}]", login);
        }
        return userDetails;
    }

    @Override
    public String normalizePassword(String password) {
        return DigestUtils.sha256Hex(password);
    }

    @Override
    public boolean contains(String login) {
        return registrantService.loginExists(login);
    }
}
