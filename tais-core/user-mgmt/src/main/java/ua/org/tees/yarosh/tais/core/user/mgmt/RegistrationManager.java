package ua.org.tees.yarosh.tais.core.user.mgmt;

import com.google.common.eventbus.AsyncEventBus;
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

import static ua.org.tees.yarosh.tais.core.common.CacheNames.GROUPS;
import static ua.org.tees.yarosh.tais.core.common.CacheNames.REGISTRANTS;
import static ua.org.tees.yarosh.tais.core.common.dto.Roles.TEACHER;

/**
 * @author Timur Yarosh
 *         Date: 09.03.14
 *         Time: 14:55
 */
@Service
public class RegistrationManager implements RegistrantService {

    private static final Logger log = LoggerFactory.getLogger(RegistrationManager.class);
    private RegistrantRepository registrantRepository;
    private StudentGroupRepository studentGroupRepository;
    private AsyncEventBus eventBus;

    @Autowired
    public void setRegistrantRepository(RegistrantRepository registrantRepository) {
        this.registrantRepository = registrantRepository;
    }

    @Autowired
    public void setStudentGroupRepository(StudentGroupRepository studentGroupRepository) {
        this.studentGroupRepository = studentGroupRepository;
    }

    @Autowired
    public void setEventBus(AsyncEventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    @CacheEvict(value = REGISTRANTS, allEntries = true)
    public Registrant createRegistration(Registrant registrant) {
        log.info("Try to create registration [login: {}]", registrant.getLogin());
        SimpleValidation.validate(registrant);
        if (registrantRepository.exists(registrant.getLogin())) {
            return null;
        }

        Registrant persistedRegistrant = registrantRepository.save(registrant);
        log.info("[login: {}] registered successfully", registrant.getLogin());
        eventBus.post(new UserRegisteredEvent(persistedRegistrant));
        return persistedRegistrant;
    }

    @Override
    public void addRegistrationListener(RegistrationListener listener) {
        eventBus.register(listener);
    }

    @Override
    @Cacheable(REGISTRANTS)
    public Registrant getRegistration(String login) {
        log.debug("Profile [login: {}] requested", login);
        boolean exists = registrantRepository.exists(login);
        return exists ? registrantRepository.findOne(login) : null;
    }

    @Override
    @CacheEvict(value = REGISTRANTS, allEntries = true)
    public Registrant updateRegistration(Registrant registrant) throws RegistrantNotFoundException {
        log.info("Registration updating for [login: {}] requested", registrant);
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
    @CacheEvict(value = REGISTRANTS, allEntries = true)
    public void deleteRegistration(String login) {
        log.info("Registration [login: {}] deleting requested", login);
        if (login == null || login.isEmpty()) {
            throw new IllegalArgumentException();
        }
        registrantRepository.delete(login);
    }

    @Override
    @Cacheable(REGISTRANTS)
    public List<Registrant> findAllRegistrants() {
        return registrantRepository.findAll();
    }

    @Override
    public List<Registrant> findAllTeachers() {
        return registrantRepository.findAllByRole(TEACHER);
    }

    @Override
    @Cacheable(REGISTRANTS)
    public boolean loginExists(String login) {
        return registrantRepository.exists(login);
    }

    @Override
    @Cacheable(GROUPS)
    public List<StudentGroup> findAllStudentGroups() {
        log.info("all groups retrieving");
        return studentGroupRepository.findAll();
    }

    @Override
    @Cacheable(GROUPS)
    public boolean isStudentGroupExists(String id) {
        return studentGroupRepository.exists(id);
    }

    @Override
    @CacheEvict(value = GROUPS, allEntries = true)
    public StudentGroup createStudentGroup(StudentGroup studentGroup) {
        return studentGroupRepository.exists(studentGroup.getId()) ?
                studentGroupRepository.findOne(studentGroup.getId()) :
                studentGroupRepository.save(studentGroup);
    }

    @Override
    @Cacheable(GROUPS)
    public StudentGroup findStudentGroup(String id) {
        return studentGroupRepository.findOne(id);
    }

    @Cacheable(GROUPS)
    public List<Registrant> findRegistrantsByStudentGroup(String id) {
        return studentGroupRepository.findRegistrantsByStudentGroup(id);
    }
}
