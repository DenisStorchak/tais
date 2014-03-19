package ua.org.tees.yarosh.tais.ui.api.views;

import com.vaadin.navigator.View;
import ua.org.tees.yarosh.tais.core.common.models.PersonalTask;

import java.util.List;

public interface PersonalWorktableView extends View {
    String VIEW_NAME = "mainView";

    interface PersonalWorktableListener {
        List<PersonalTask> listRegistrantTasks();
    }

    void addListener(PersonalWorktableListener listener);
}
