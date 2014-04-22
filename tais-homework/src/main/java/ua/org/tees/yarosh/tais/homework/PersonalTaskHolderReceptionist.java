package ua.org.tees.yarosh.tais.homework;

import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.Subscribe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.org.tees.yarosh.tais.core.user.mgmt.UserRegisteredEvent;
import ua.org.tees.yarosh.tais.homework.api.persistence.PersonalTaskHolderRepository;
import ua.org.tees.yarosh.tais.homework.models.PersonalTaskHolder;

import java.util.ArrayList;

import static ua.org.tees.yarosh.tais.core.user.mgmt.api.service.RegistrantService.RegistrationListener;

public class PersonalTaskHolderReceptionist implements RegistrationListener {

    public static final Logger log = LoggerFactory.getLogger(PersonalTaskHolderReceptionist.class);
    private PersonalTaskHolderRepository personalTaskHolderRepository;

    public PersonalTaskHolderReceptionist(PersonalTaskHolderRepository personalTaskHolderRepository) {
        this.personalTaskHolderRepository = personalTaskHolderRepository;
    }

    @Subscribe
    @AllowConcurrentEvents
    @Override
    public void onRegistered(UserRegisteredEvent event) {
        log.debug("PersonalTaskHolder will be created for new registrant [{}]", event.getRegistrant().getLogin());
        PersonalTaskHolder personalTaskHolder = new PersonalTaskHolder();
        personalTaskHolder.setOwner(event.getRegistrant());
        personalTaskHolder.setManualTaskList(new ArrayList<>());
        personalTaskHolder.setQuestionsSuiteList(new ArrayList<>());
        personalTaskHolderRepository.saveAndFlush(personalTaskHolder);
    }
}
