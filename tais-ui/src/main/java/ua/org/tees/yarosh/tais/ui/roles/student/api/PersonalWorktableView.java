package ua.org.tees.yarosh.tais.ui.roles.student.api;

import ua.org.tees.yarosh.tais.homework.models.ManualTask;
import ua.org.tees.yarosh.tais.homework.models.QuestionsSuite;
import ua.org.tees.yarosh.tais.ui.core.mvp.TaisView;

import java.util.List;

public interface PersonalWorktableView extends TaisView {
    String VIEW_NAME = "mainView";

    interface PersonalWorktableListener {
        List<ManualTask> listActualUnresolvedManualTasks();

        List<QuestionsSuite> listActualQuestionsSuites();
    }
}
