package ua.org.tees.yarosh.tais.homework;

import ua.org.tees.yarosh.tais.homework.models.ManualAchievement;
import ua.org.tees.yarosh.tais.homework.models.ManualTask;

import java.util.List;

public class TaskUtils {
    public static boolean isRated(ManualTask manualTask, List<ManualAchievement> manualAchievements) {
        return manualAchievements.stream().anyMatch(a -> a.getManualTask().getId().equals(manualTask));
    }
}
