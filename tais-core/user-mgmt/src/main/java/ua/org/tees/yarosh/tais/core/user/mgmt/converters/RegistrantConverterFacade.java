package ua.org.tees.yarosh.tais.core.user.mgmt.converters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.org.tees.yarosh.tais.core.common.models.Registrant;
import ua.org.tees.yarosh.tais.core.user.mgmt.models.RegistrantEntity;

/**
 * @author Timur Yarosh
 *         Date: 09.03.14
 *         Time: 15:15
 */
@Service
public class RegistrantConverterFacade {
    @Autowired
    private RegistrantToRegistrantEntityConverter registrantToRegistrantEntityConverter;
    private static final Logger LOGGER = LoggerFactory.getLogger(RegistrantConverterFacade.class);

    public RegistrantEntity convert(Registrant registrant, Class<RegistrantEntity> entityClass) {
        log(entityClass);
        return registrantToRegistrantEntityConverter.from(registrant);
    }

    public Registrant convert(RegistrantEntity registrantEntity, Class<Registrant> registrantClass) {
        log(registrantClass);
        return registrantToRegistrantEntityConverter.to(registrantEntity);
    }

    private void log(Class<?> target) {
        LOGGER.debug("Target class is {}", target.getName());
    }
}
