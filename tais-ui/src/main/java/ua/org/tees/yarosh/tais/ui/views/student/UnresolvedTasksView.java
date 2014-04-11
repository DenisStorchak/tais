package ua.org.tees.yarosh.tais.ui.views.student;

import com.vaadin.navigator.ViewChangeListener;
import org.springframework.beans.factory.annotation.Qualifier;
import ua.org.tees.yarosh.tais.auth.annotations.PermitRoles;
import ua.org.tees.yarosh.tais.homework.models.ManualTask;
import ua.org.tees.yarosh.tais.homework.models.QuestionsSuite;
import ua.org.tees.yarosh.tais.ui.components.layouts.DashboardView;
import ua.org.tees.yarosh.tais.ui.core.mvp.PresentedBy;
import ua.org.tees.yarosh.tais.ui.core.mvp.TaisView;
import ua.org.tees.yarosh.tais.ui.views.student.api.UnresolvedTasksTaisView;

import java.util.List;

import static ua.org.tees.yarosh.tais.core.common.dto.Roles.STUDENT;
import static ua.org.tees.yarosh.tais.ui.core.DataBinds.UriFragments.Student.UNRESOLVED;
import static ua.org.tees.yarosh.tais.ui.views.student.api.UnresolvedTasksTaisView.UnresolvedTasksPresenter;

@Qualifier(UNRESOLVED)
@PresentedBy(UnresolvedTasksPresenter.class)
@PermitRoles(STUDENT)
@TaisView("Невыполненные задания")
public class UnresolvedTasksView extends DashboardView implements UnresolvedTasksTaisView {
    @Override
    public void setUnresolvedManualTasks(List<ManualTask> manualTasks) {

    }

    @Override
    public void setUnresolvedQuestionsSuites(List<QuestionsSuite> questionsSuites) {

    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

    }
}
