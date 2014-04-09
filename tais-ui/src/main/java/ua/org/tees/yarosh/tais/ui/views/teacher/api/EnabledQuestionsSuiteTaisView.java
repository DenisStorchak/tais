package ua.org.tees.yarosh.tais.ui.views.teacher.api;

import com.vaadin.navigator.View;
import ua.org.tees.yarosh.tais.core.common.models.StudentGroup;
import ua.org.tees.yarosh.tais.homework.models.QuestionsSuite;
import ua.org.tees.yarosh.tais.ui.core.api.UpdatableView;
import ua.org.tees.yarosh.tais.ui.core.mvp.Presenter;

import java.util.List;

public interface EnabledQuestionsSuiteTaisView extends View, UpdatableView {

    void setSuites(List<QuestionsSuite> questionsSuites);

    void setGroups(List<StudentGroup> studentGroups);

    interface EnabledQuestionsSuitePresenter extends Presenter {
        void onDelete(long id, StudentGroup studentGroup);

        void onDetails(long id);

        void onSearch(StudentGroup studentGroup);
    }
}
