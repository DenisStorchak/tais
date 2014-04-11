package ua.org.tees.yarosh.tais.homework;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.org.tees.yarosh.tais.core.common.models.Registrant;
import ua.org.tees.yarosh.tais.homework.api.persistence.AchievementDiaryRepository;
import ua.org.tees.yarosh.tais.homework.api.persistence.PersonalTaskHolderRepository;
import ua.org.tees.yarosh.tais.homework.models.AchievementDiary;
import ua.org.tees.yarosh.tais.homework.models.PersonalTaskHolder;

import static com.google.common.collect.Lists.newArrayList;

/**
 * @author Timur Yarosh
 *         Date: 11.04.14
 *         Time: 20:04
 */
@Aspect
@Component
public class RegistrationAspect {

    private static final Logger log = LoggerFactory.getLogger(RegistrationAspect.class);
    private AchievementDiaryRepository achievementDiaryRepository;
    private PersonalTaskHolderRepository personalTaskHolderRepository;

    @Autowired
    public void setAchievementDiaryRepository(AchievementDiaryRepository achievementDiaryRepository) {
        this.achievementDiaryRepository = achievementDiaryRepository;
    }

    @Autowired
    public void setPersonalTaskHolderRepository(PersonalTaskHolderRepository personalTaskHolderRepository) {
        this.personalTaskHolderRepository = personalTaskHolderRepository;
    }

    @AfterReturning(pointcut = "registrationPointcut()", returning = "registrant")
    public void createAndFlushAchievementDiary(Registrant registrant) {
        log.debug("AchievementDiary will be created for new registrant [{}]", registrant.getLogin());
        AchievementDiary achievementDiary = new AchievementDiary();
        achievementDiary.setAutoAchievements(newArrayList());
        achievementDiary.setManualAchievements(newArrayList());
        achievementDiary.setOwner(registrant);
        achievementDiaryRepository.saveAndFlush(achievementDiary);
    }

    @AfterReturning(pointcut = "registrationPointcut()", returning = "registrant")
    public void createAndFlushPersonalTaskHolder(Registrant registrant) {
        log.debug("PersonalTaskHolder will be created for new registrant [{}]", registrant.getLogin());
        PersonalTaskHolder personalTaskHolder = new PersonalTaskHolder();
        personalTaskHolder.setOwner(registrant);
        personalTaskHolder.setManualTaskList(newArrayList());
        personalTaskHolder.setQuestionsSuiteList(newArrayList());
        personalTaskHolderRepository.saveAndFlush(personalTaskHolder);
    }

    @Pointcut("execution(* ua.org.tees.yarosh.tais.core.user.mgmt.api.service.RegistrantService.createRegistration(..))")
    private void registrationPointcut() {
    }
}
