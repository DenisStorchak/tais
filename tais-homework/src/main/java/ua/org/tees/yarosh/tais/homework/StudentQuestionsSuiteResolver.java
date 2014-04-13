package ua.org.tees.yarosh.tais.homework;

import com.google.common.eventbus.AsyncEventBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import ua.org.tees.yarosh.tais.core.common.CacheNames;
import ua.org.tees.yarosh.tais.homework.api.QuestionsSuiteResolver;
import ua.org.tees.yarosh.tais.homework.api.persistence.AchievementDiaryRepository;
import ua.org.tees.yarosh.tais.homework.events.QuestionsSuiteResolvedEvent;
import ua.org.tees.yarosh.tais.homework.models.AchievementDiary;
import ua.org.tees.yarosh.tais.homework.models.Answer;
import ua.org.tees.yarosh.tais.homework.models.AutoAchievement;

@Service
public class StudentQuestionsSuiteResolver implements QuestionsSuiteResolver {

    private static final int MAX_GRADE = 100;
    private static final int MIN_GRADE = 0;
    private AchievementDiaryRepository diaryRepository;
    private AsyncEventBus eventBus;

    @Autowired
    public void setDiaryRepository(AchievementDiaryRepository diaryRepository) {
        this.diaryRepository = diaryRepository;
    }

    @Autowired
    public void setEventBus(AsyncEventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    @CacheEvict(value = CacheNames.QUESTION_SUITES_RESULTS, allEntries = true)
    public int resolve(QuestionsSuiteReport report) {
        AchievementDiary diary = diaryRepository.findOne(report.getOwner());
        AutoAchievement achievement = calculate(report);
        diary.getAutoAchievements().add(achievement);
        diaryRepository.saveAndFlush(diary);
        eventBus.post(new QuestionsSuiteResolvedEvent(report));
        return achievement.getGrade();
    }

    private AutoAchievement calculate(QuestionsSuiteReport result) {
        int grade = MIN_GRADE;
        int answerWeight = MAX_GRADE / result.getQuestionsSuite().getQuestions().size();

        AutoAchievement autoAchievement = new AutoAchievement();
        autoAchievement.setQuestionsSuite(result.getQuestionsSuite());
        for (Answer answer : result.getAnswers().values()) {
            if (answer.getTruth()) {
                grade += answerWeight;
            }
        }
        autoAchievement.setGrade(grade);
        return autoAchievement;
    }
}
