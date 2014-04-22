package ua.org.tees.yarosh.tais.homework;

import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.Subscribe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.org.tees.yarosh.tais.core.user.mgmt.UserRegisteredEvent;
import ua.org.tees.yarosh.tais.homework.api.persistence.AchievementDiaryRepository;
import ua.org.tees.yarosh.tais.homework.models.AchievementDiary;

import java.util.ArrayList;

import static ua.org.tees.yarosh.tais.core.user.mgmt.api.service.RegistrantService.RegistrationListener;

public class AchievementDiaryReceptionist implements RegistrationListener {

    public static final Logger log = LoggerFactory.getLogger(AchievementDiaryReceptionist.class);
    private AchievementDiaryRepository achievementDiaryRepository;

    public AchievementDiaryReceptionist(AchievementDiaryRepository achievementDiaryRepository) {
        this.achievementDiaryRepository = achievementDiaryRepository;
    }

    @Subscribe
    @AllowConcurrentEvents
    @Override
    public void onRegistered(UserRegisteredEvent event) {
        log.debug("AchievementDiary will be created for new registrant [{}]", event.getRegistrant().getLogin());
        AchievementDiary achievementDiary = new AchievementDiary();
        achievementDiary.setAutoAchievements(new ArrayList<>());
        achievementDiary.setManualAchievements(new ArrayList<>());
        achievementDiary.setOwner(event.getRegistrant());
        achievementDiaryRepository.saveAndFlush(achievementDiary);
    }
}
