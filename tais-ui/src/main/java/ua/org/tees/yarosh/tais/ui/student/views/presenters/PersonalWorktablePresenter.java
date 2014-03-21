package ua.org.tees.yarosh.tais.ui.student.views.presenters;

import org.springframework.stereotype.Service;
import ua.org.tees.yarosh.tais.homework.models.ManualTask;
import ua.org.tees.yarosh.tais.ui.student.views.api.PersonalWorktableView;

import java.util.List;

@Service
public class PersonalWorktablePresenter implements PersonalWorktableView.PersonalWorktableListener {

    @Override
    public List<ManualTask> listRegistrantTasks() {
        return null;
    }
}
