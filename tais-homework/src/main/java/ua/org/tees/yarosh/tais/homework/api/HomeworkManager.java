package ua.org.tees.yarosh.tais.homework.api;

import ua.org.tees.yarosh.tais.core.common.models.Discipline;
import ua.org.tees.yarosh.tais.core.common.models.GroupTask;

public interface HomeworkManager {
    long createGeneralTask(GroupTask task);

    void enableGroupTask(long id);

    void disableGroupTask(long id);
}
