package ua.org.tees.yarosh.tais.homework.events;

import ua.org.tees.yarosh.tais.homework.models.ManualTask;

/**
 * @author Timur Yarosh
 *         Date: 12.04.14
 *         Time: 22:11
 */
public class ManualTaskEnabledEvent {

    private ManualTask task;

    public ManualTask getTask() {
        return task;
    }

    public ManualTaskEnabledEvent(ManualTask task) {
        this.task = task;
    }
}
