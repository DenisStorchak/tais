package ua.org.tees.yarosh.tais.ui.views.student.presenters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import ua.org.tees.yarosh.tais.homework.models.ManualTask;
import ua.org.tees.yarosh.tais.homework.models.QuestionsSuite;
import ua.org.tees.yarosh.tais.ui.core.api.Updatable;
import ua.org.tees.yarosh.tais.ui.core.mvp.AbstractPresenter;
import ua.org.tees.yarosh.tais.ui.core.mvp.TaisPresenter;

import static ua.org.tees.yarosh.tais.ui.core.DataBinds.UriFragments.Student.UNRESOLVED;
import static ua.org.tees.yarosh.tais.ui.views.student.api.UnresolvedTasksTaisView.UnresolvedTasksPresenter;

@TaisPresenter
public class UnresolvedTasksListener extends AbstractPresenter implements UnresolvedTasksPresenter {

    @Autowired
    public UnresolvedTasksListener(@Qualifier(UNRESOLVED) Updatable view) {
        super(view);
    }

    @Override
    public void onUpdate() {

    }

    @Override
    public void onQuestionsSuite(QuestionsSuite questionsSuite) {

    }

    @Override
    public void onManualTask(ManualTask manualTask) {

    }
}
