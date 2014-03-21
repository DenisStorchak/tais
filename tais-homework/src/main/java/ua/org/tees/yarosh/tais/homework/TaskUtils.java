package ua.org.tees.yarosh.tais.homework;

import ua.org.tees.yarosh.tais.homework.models.ManualAchievement;
import ua.org.tees.yarosh.tais.homework.models.ManualTask;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import static ua.org.tees.yarosh.tais.homework.TimeUtils.minusDays;
import static ua.org.tees.yarosh.tais.homework.TimeUtils.toDate;

public class TaskUtils {
    public static boolean isRated(ManualTask manualTask, List<ManualAchievement> manualAchievements) {
        return manualAchievements.stream().anyMatch(a -> a.getManualTask().getId().equals(manualTask.getId()));
    }

    public static boolean isDeadlineAfter(ManualTask manualTask, int daysBeforeDeadline) {
        return minusDays(manualTask.getDeadline(), daysBeforeDeadline).equals(toDate(LocalDate.now()));
    }

    public static boolean isTaskOverdue(ManualTask manualTask) {
        return manualTask.getDeadline().before(new Date());
    }
}
