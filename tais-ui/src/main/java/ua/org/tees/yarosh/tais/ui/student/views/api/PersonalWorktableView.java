package ua.org.tees.yarosh.tais.ui.student.views.api;

import com.vaadin.navigator.View;
import ua.org.tees.yarosh.tais.homework.models.ManualTask;

import java.util.List;

public interface PersonalWorktableView extends View {
    String VIEW_NAME = "mainView";

    interface PersonalWorktableListener {
        List<ManualTask> listRegistrantTasks();
    }

    void addListener(PersonalWorktableListener listener);
}
