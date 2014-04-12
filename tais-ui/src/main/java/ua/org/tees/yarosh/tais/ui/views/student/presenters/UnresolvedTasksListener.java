package ua.org.tees.yarosh.tais.ui.views.student.presenters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import ua.org.tees.yarosh.tais.homework.api.HomeworkManager;
import ua.org.tees.yarosh.tais.homework.models.ManualTask;
import ua.org.tees.yarosh.tais.homework.models.QuestionsSuite;
import ua.org.tees.yarosh.tais.ui.core.Registrants;
import ua.org.tees.yarosh.tais.ui.core.api.Updateable;
import ua.org.tees.yarosh.tais.ui.core.mvp.AbstractPresenter;
import ua.org.tees.yarosh.tais.ui.core.mvp.TaisPresenter;
import ua.org.tees.yarosh.tais.ui.views.student.api.UnresolvedTasksTaisView;

import static ua.org.tees.yarosh.tais.ui.core.DataBinds.UriFragments.Student.UNRESOLVED;
import static ua.org.tees.yarosh.tais.ui.views.student.api.UnresolvedTasksTaisView.UnresolvedTasksPresenter;

@TaisPresenter
public class UnresolvedTasksListener extends AbstractPresenter implements UnresolvedTasksPresenter {

    private HomeworkManager homeworkManager;

    @Autowired
    public void setHomeworkManager(HomeworkManager homeworkManager) {
        this.homeworkManager = homeworkManager;
    }

    @Autowired
    public UnresolvedTasksListener(@Qualifier(UNRESOLVED) Updateable view) {
        super(view);
    }

    @Override
    public void onRefresh() {
        init();
    }

    @Override
    public void init() {
        UnresolvedTasksTaisView view = getView(UnresolvedTasksTaisView.class);
        view.setUnresolvedManualTasks(homeworkManager.findUnresolvedActualManualTasks(Registrants.getCurrent()));
        view.setUnresolvedQuestionsSuites(homeworkManager.findUnresolvedActualQuestionsSuite(Registrants.getCurrent()));
    }

    @Override
    public void onQuestionsSuite(QuestionsSuite questionsSuite) {

    }

    @Override
    public void onManualTask(ManualTask manualTask) {

    }
}
