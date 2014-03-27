package ua.org.tees.yarosh.tais.core.user.mgmt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ua.org.tees.yarosh.tais.auth.AbstractAuthConfiguration;
import ua.org.tees.yarosh.tais.auth.AuthDao;
import ua.org.tees.yarosh.tais.auth.UserDetails;
import ua.org.tees.yarosh.tais.core.common.dto.Role;
import ua.org.tees.yarosh.tais.core.user.mgmt.api.service.RegistrantService;

@Configuration
public class TaisAuthConfiguration extends AbstractAuthConfiguration {

    @Autowired
    private RegistrantService registrantService;

    @Bean
    public AuthDao authDao() {
        return login -> {
//            UserDetails userDetails = null; //todo uncomment
//            Registrant registration = registrantService.getRegistration(login);
//            if (registration != null) {
//                userDetails = new UserDetails();
//                userDetails.setUsername(registration.getLogin());
//                userDetails.setPassword(registration.getPassword());
//                userDetails.setRole(registration.getRole().toString());
//            }
//            return userDetails;

            UserDetails userDetails = new UserDetails();
            userDetails.setUsername("admin");
            userDetails.setPassword("admin");
            userDetails.setRole(Role.ADMIN);
            return userDetails;
        };
    }
}
