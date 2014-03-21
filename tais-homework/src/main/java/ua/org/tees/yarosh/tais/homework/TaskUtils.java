package ua.org.tees.yarosh.tais.homework;

import ua.org.tees.yarosh.tais.homework.models.AutoAchievement;
import ua.org.tees.yarosh.tais.homework.models.ManualAchievement;
import ua.org.tees.yarosh.tais.homework.models.ManualTask;
import ua.org.tees.yarosh.tais.homework.models.QuestionsSuite;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import static ua.org.tees.yarosh.tais.homework.TimeUtils.minusDays;
import static ua.org.tees.yarosh.tais.homework.TimeUtils.toDate;

public class TaskUtils {
    public static boolean isRated(ManualTask manualTask, List<ManualAchievement> manualAchievements) {
        return manualAchievements.stream().anyMatch(a -> a.getManualTask().getId().equals(manualTask.getId()));
    }

    public static boolean isRated(QuestionsSuite questionsSuite, List<AutoAchievement> autoAchievements) {
        return autoAchievements.stream().anyMatch(a -> a.getQuestionsSuite().getId().equals(questionsSuite.getId()));
    }

    public static boolean isDeadlineAfter(ManualTask manualTask, int daysBeforeDeadline) {
        return minusDays(manualTask.getDeadline(), daysBeforeDeadline).equals(toDate(LocalDate.now()));
    }

    public static boolean isDeadlineAfter(QuestionsSuite questionsSuite, int daysBeforeDeadline) {
        return minusDays(questionsSuite.getDeadline(), daysBeforeDeadline).equals(toDate(LocalDate.now()));
    }

    public static boolean isTaskOverdue(ManualTask manualTask) {
        return manualTask.getDeadline().before(new Date());
    }

    public static boolean isTaskOverdue(QuestionsSuite questionsSuite) {
        return questionsSuite.getDeadline().before(new Date());
    }
}
