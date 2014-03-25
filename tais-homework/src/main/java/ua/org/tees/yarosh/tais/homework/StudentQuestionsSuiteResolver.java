package ua.org.tees.yarosh.tais.homework;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.org.tees.yarosh.tais.homework.api.QuestionsSuiteResolver;
import ua.org.tees.yarosh.tais.homework.api.persistence.AchievementDiaryRepository;
import ua.org.tees.yarosh.tais.homework.models.AchievementDiary;
import ua.org.tees.yarosh.tais.homework.models.Answer;
import ua.org.tees.yarosh.tais.homework.models.AutoAchievement;
import ua.org.tees.yarosh.tais.homework.models.ManualAchievement;

import java.util.ArrayList;

@Service
public class StudentQuestionsSuiteResolver implements QuestionsSuiteResolver {

    private static final int MAX_GRADE = 100;
    private static final int MIN_GRADE = 0;
    private AchievementDiaryRepository diaryRepository;

    @Autowired
    public void setDiaryRepository(AchievementDiaryRepository diaryRepository) {
        this.diaryRepository = diaryRepository;
    }

    @Override
    public void resolve(QuestionsSuiteResult result) {
        AchievementDiary diary = diaryRepository.findOne(result.getOwner().getLogin());
        if (diary == null) {
            diary = new AchievementDiary();
            diary.setOwner(result.getOwner());
            diary.setAutoAchievements(new ArrayList<AutoAchievement>());
            diary.setManualAchievements(new ArrayList<ManualAchievement>());
        }
        diary.getAutoAchievements().add(calculate(result));
        diaryRepository.saveAndFlush(diary);
    }

    private AutoAchievement calculate(QuestionsSuiteResult result) {
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
