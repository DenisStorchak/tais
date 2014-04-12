package ua.org.tees.yarosh.tais.homework.events;

import ua.org.tees.yarosh.tais.homework.models.ManualTask;

/**
 * @author Timur Yarosh
 *         Date: 12.04.14
 *         Time: 23:03
 */
public class ManualTaskRemovedEvent {

    private ManualTask task;

    public ManualTask getTask() {
        return task;
    }

    public ManualTaskRemovedEvent(ManualTask manualTask) {
        this.task = manualTask;
    }
}
