package ua.org.tees.yarosh.tais.homework.util;

import ua.org.tees.yarosh.tais.homework.models.AutoAchievement;
import ua.org.tees.yarosh.tais.homework.models.ManualAchievement;
import ua.org.tees.yarosh.tais.homework.models.ManualTask;
import ua.org.tees.yarosh.tais.homework.models.QuestionsSuite;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public abstract class TaskUtils {
    public static boolean isRated(ManualTask manualTask, List<ManualAchievement> manualAchievements) {
        return manualAchievements.stream().anyMatch(a -> a.getManualTask().getId().equals(manualTask.getId()));
    }

    public static boolean isRated(QuestionsSuite questionsSuite, List<AutoAchievement> autoAchievements) {
        return autoAchievements.stream().anyMatch(a -> a.getQuestionsSuite().getId().equals(questionsSuite.getId()));
    }

    public static boolean isDeadlineAfter(ManualTask manualTask, int daysBeforeDeadline) {
        return TimeUtils.minusDays(manualTask.getDeadline(), daysBeforeDeadline).equals(TimeUtils.toDate(LocalDate.now()));
    }

    public static boolean isDeadlineAfter(QuestionsSuite questionsSuite, int daysBeforeDeadline) {
        return TimeUtils.minusDays(questionsSuite.getDeadline(), daysBeforeDeadline).equals(TimeUtils.toDate(LocalDate.now()));
    }

    public static boolean isTaskOverdue(ManualTask manualTask) {
        return manualTask.getDeadline().before(new Date());
    }

    public static boolean isTaskOverdue(QuestionsSuite questionsSuite) {
        return questionsSuite.getDeadline().before(new Date());
    }
}
