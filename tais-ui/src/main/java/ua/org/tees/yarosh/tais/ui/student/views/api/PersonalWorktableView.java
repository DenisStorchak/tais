package ua.org.tees.yarosh.tais.ui.student.views.api;

import ua.org.tees.yarosh.tais.homework.models.ManualTask;

import java.util.List;

public interface PersonalWorktableView extends TaisView {
    String VIEW_NAME = "mainView";

    interface PersonalWorktableListener {
        List<ManualTask> listActualUnresolvedTasks();
    }

    void addListener(PersonalWorktableListener listener);
}
