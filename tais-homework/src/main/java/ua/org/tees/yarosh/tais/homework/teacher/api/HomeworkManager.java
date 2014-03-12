package ua.org.tees.yarosh.tais.homework.teacher.api;

import ua.org.tees.yarosh.tais.core.common.dto.Discipline;
import ua.org.tees.yarosh.tais.core.common.dto.GeneralTask;

public interface HomeworkManager {
    long createGeneralTask(GeneralTask task, String group, Discipline discipline);

    void enableGeneralTask(long id);

    void disableGeneralTask(long id);

    long find(String description, String group, Discipline discipline);
}
