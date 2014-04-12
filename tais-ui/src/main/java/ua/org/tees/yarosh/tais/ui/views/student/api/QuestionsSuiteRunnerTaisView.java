package ua.org.tees.yarosh.tais.ui.views.student.api;

import com.vaadin.navigator.View;
import ua.org.tees.yarosh.tais.homework.models.Answer;
import ua.org.tees.yarosh.tais.homework.models.Question;
import ua.org.tees.yarosh.tais.homework.models.QuestionsSuite;
import ua.org.tees.yarosh.tais.ui.core.api.Updateable;
import ua.org.tees.yarosh.tais.ui.core.mvp.Presenter;

import java.util.List;

/**
 * @author Timur Yarosh
 *         Date: 12.04.14
 *         Time: 11:35
 */
public interface QuestionsSuiteRunnerTaisView extends View, Updateable {

    void setQuestion(Question question);

    void setRemainingQuestions(int remaining);

    void setProgress(float progress);

    interface QuestionsSuiteRunnerPresenter extends Presenter {
        void onAnswered(List<Answer> answers);

        void setQuestionsSuite(QuestionsSuite suite);
    }
}
