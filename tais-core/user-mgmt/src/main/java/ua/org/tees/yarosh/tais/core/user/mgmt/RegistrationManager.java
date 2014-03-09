package ua.org.tees.yarosh.tais.core.user.mgmt;

import com.google.java.contract.Requires;
import org.hibernate.validator.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.org.tees.yarosh.tais.core.common.api.SimpleValidation;
import ua.org.tees.yarosh.tais.core.common.dto.Registrant;
import ua.org.tees.yarosh.tais.core.common.exceptions.RegistrantNotFoundException;
import ua.org.tees.yarosh.tais.core.user.mgmt.api.persistence.RegistrantRepository;
import ua.org.tees.yarosh.tais.core.user.mgmt.api.service.RegistrantService;
import ua.org.tees.yarosh.tais.core.user.mgmt.converters.RegistrantConverterFacade;
import ua.org.tees.yarosh.tais.core.user.mgmt.models.RegistrantEntity;

import javax.validation.constraints.NotNull;

/**
 * @author Timur Yarosh
 *         Date: 09.03.14
 *         Time: 14:55
 */
@Service
public class RegistrationManager implements RegistrantService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationManager.class);
    @Autowired
    private RegistrantRepository registrantRepository;
    @Autowired
    private RegistrantConverterFacade converter;

    @Override
    @Requires({
            "registrant != null"
    })
    public Registrant createRegistration(Registrant registrant) {
        LOGGER.info("Try to create registration [login: {}]", registrant.getLogin());
        SimpleValidation.validate(registrant);

        RegistrantEntity registrantEntity = converter.convert(registrant, RegistrantEntity.class);
        registrantRepository.save(registrantEntity);
        LOGGER.info("[login: {}] registered successfully", registrant.getLogin());
        return registrant;
    }

    @Override
    @Requires({
            "login != null",
            "!login.isEmpty()"
    })
    public Registrant getRegistration(String login) throws RegistrantNotFoundException {
        LOGGER.info("Profile [login: {}] requested", login);
        RegistrantEntity registrantEntity = registrantRepository.findOne(login);
        if (registrantEntity == null) {
            throw new RegistrantNotFoundException(login);
        }
        return converter.convert(registrantEntity, Registrant.class);
    }

    @Override
    @Requires({
            "registrant != null"
    })
    public Registrant updateRegistration(Registrant registrant) throws RegistrantNotFoundException {
        LOGGER.info("Registration updating for [login: {}] requested", registrant.getLogin());
        SimpleValidation.validate(registrant);
        if (registrantRepository.exists(registrant.getLogin())) {
            RegistrantEntity registrantEntity = converter.convert(registrant, RegistrantEntity.class);
            registrantRepository.save(registrantEntity);
            return registrant;
        }
        throw new RegistrantNotFoundException(registrant.getLogin());
    }

    @Override
    @Requires({
            "login != null",
            "!login.isEmpty()"
    })
    public void deleteRegistration(@NotNull @NotBlank String login) {
        LOGGER.info("Registration [login: {}] deleting requested", login);
        registrantRepository.delete(login);
    }
}
