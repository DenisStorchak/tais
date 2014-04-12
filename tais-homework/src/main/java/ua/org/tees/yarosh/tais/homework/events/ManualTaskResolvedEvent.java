package ua.org.tees.yarosh.tais.homework.events;

import ua.org.tees.yarosh.tais.homework.models.ManualTaskReport;

/**
 * @author Timur Yarosh
 *         Date: 12.04.14
 *         Time: 22:12
 */
public class ManualTaskResolvedEvent {

    private ManualTaskReport task;

    public ManualTaskReport getTask() {
        return task;
    }

    public ManualTaskResolvedEvent(ManualTaskReport task) {
        this.task = task;
    }
}
