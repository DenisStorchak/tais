package ua.org.tees.yarosh.tais.core.user.mgmt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ua.org.tees.yarosh.tais.core.common.api.SimpleValidation;
import ua.org.tees.yarosh.tais.core.common.exceptions.RegistrantNotFoundException;
import ua.org.tees.yarosh.tais.core.common.models.Registrant;
import ua.org.tees.yarosh.tais.core.common.models.StudentGroup;
import ua.org.tees.yarosh.tais.core.user.mgmt.api.persistence.RegistrantRepository;
import ua.org.tees.yarosh.tais.core.user.mgmt.api.persistence.StudentGroupRepository;
import ua.org.tees.yarosh.tais.core.user.mgmt.api.service.RegistrantService;

import java.util.List;

import static ua.org.tees.yarosh.tais.core.common.CacheNames.GROUP;
import static ua.org.tees.yarosh.tais.core.common.CacheNames.REGISTRATION;

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
    private StudentGroupRepository studentGroupRepository;

    @Override
    @CacheEvict(REGISTRATION)
    public Registrant createRegistration(Registrant registrant) {
        LOGGER.info("Try to create registration [login: {}]", registrant.getLogin());
        SimpleValidation.validate(registrant);
        registrant.getGroup().getStudents().add(registrant);

        Registrant persistedRegistrant = registrantRepository.save(registrant);
        LOGGER.info("[login: {}] registered successfully", registrant.getLogin());
        return persistedRegistrant;
    }

    @Override
    @Cacheable(REGISTRATION)
    public Registrant getRegistration(String login) {
        LOGGER.info("Profile [login: {}] requested", login);
        return registrantRepository.findOne(login);
    }

    @Override
    @CacheEvict(REGISTRATION)
    public Registrant updateRegistration(Registrant registrant) throws RegistrantNotFoundException {
        LOGGER.info("Registration updating for [login: {}] requested", registrant);
        if (registrant == null) {
            throw new IllegalArgumentException(Registrant.class.getName() + " can't be null");
        }
        SimpleValidation.validate(registrant);
        if (registrantRepository.exists(registrant.getLogin())) {
            return registrantRepository.saveAndFlush(registrant);
        }
        throw new RegistrantNotFoundException(registrant.getLogin());
    }

    @Override
    @CacheEvict(REGISTRATION)
    public void deleteRegistration(String login) {
        LOGGER.info("Registration [login: {}] deleting requested", login);
        if (login == null || login.isEmpty()) {
            throw new IllegalArgumentException();
        }
        registrantRepository.delete(login);
    }

    @Override
    @Cacheable(REGISTRATION)
    public List<Registrant> listAllRegistrants() {
        return registrantRepository.findAll();
    }

    @Override
    @Cacheable(REGISTRATION)
    public boolean loginExists(String login) {
        return registrantRepository.exists(login);
    }

    @Override
    @Cacheable(GROUP)
    public List<StudentGroup> listStudentGroups() {
        return studentGroupRepository.findAll();
    }

    @Override
    @Cacheable(GROUP)
    public boolean isStudentGroupExists(String id) {
        return studentGroupRepository.exists(id);
    }

    @Override
    @CacheEvict(GROUP)
    public StudentGroup createStudentGroup(StudentGroup studentGroup) {
        return studentGroupRepository.exists(studentGroup.getId()) ?
                studentGroupRepository.findOne(studentGroup.getId()) :
                studentGroupRepository.save(studentGroup);
    }

    @Override
    @Cacheable(GROUP)
    public StudentGroup findStudentGroup(String id) {
        return studentGroupRepository.findOne(id);
    }
}
