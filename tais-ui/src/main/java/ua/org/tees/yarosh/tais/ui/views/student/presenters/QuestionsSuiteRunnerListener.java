package ua.org.tees.yarosh.tais.ui.views.student.presenters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import ua.org.tees.yarosh.tais.homework.QuestionsSuiteReport;
import ua.org.tees.yarosh.tais.homework.api.HomeworkResolver;
import ua.org.tees.yarosh.tais.homework.models.Answer;
import ua.org.tees.yarosh.tais.homework.models.QuestionsSuite;
import ua.org.tees.yarosh.tais.ui.core.Registrants;
import ua.org.tees.yarosh.tais.ui.core.api.Updateable;
import ua.org.tees.yarosh.tais.ui.core.mvp.AbstractPresenter;
import ua.org.tees.yarosh.tais.ui.core.mvp.TaisPresenter;
import ua.org.tees.yarosh.tais.ui.views.student.api.QuestionsSuiteRunnerTaisView;

import java.util.HashMap;
import java.util.List;

import static ua.org.tees.yarosh.tais.ui.core.DataBinds.UriFragments.Student.QUESTIONS_RUNNER;
import static ua.org.tees.yarosh.tais.ui.views.student.api.QuestionsSuiteRunnerTaisView.QuestionsSuiteRunnerPresenter;

/**
 * @author Timur Yarosh
 *         Date: 12.04.14
 *         Time: 12:26
 */
@TaisPresenter
public class QuestionsSuiteRunnerListener extends AbstractPresenter implements QuestionsSuiteRunnerPresenter {

    private QuestionsSuiteReport report;
    private HomeworkResolver homeworkResolver;
    private int currentQuestionIndex = 0;

    @Autowired
    public void setHomeworkResolver(HomeworkResolver homeworkResolver) {
        this.homeworkResolver = homeworkResolver;
    }

    @Autowired
    public QuestionsSuiteRunnerListener(@Qualifier(QUESTIONS_RUNNER) Updateable view) {
        super(view);
    }

    @Override
    public void onAnswered(List<Answer> answers) {
        QuestionsSuiteRunnerTaisView view = getView(QuestionsSuiteRunnerTaisView.class);
        QuestionsSuite suite = report.getQuestionsSuite();
        if ((suite.getQuestions().size() - 1) != report.getAnswers().size()) {
            answers.forEach(a -> report.getAnswers().put(a.getQuestion(), a));

            view.setQuestion(suite.getQuestions().get(++currentQuestionIndex));
            view.setProgress((suite.getQuestions().size() / 100) * currentQuestionIndex);
            view.setRemainingQuestions(suite.getQuestions().size() - currentQuestionIndex);
        } else {
            answers.forEach(a -> report.getAnswers().put(a.getQuestion(), a));

            view.setProgress((suite.getQuestions().size() / 100) * currentQuestionIndex);
            view.setRemainingQuestions(suite.getQuestions().size() - currentQuestionIndex);

            homeworkResolver.resolve(report);
            //todo create congrats window
        }
    }

    @Override
    public void setQuestionsSuite(QuestionsSuite suite) {
        report = new QuestionsSuiteReport();
        report.setQuestionsSuite(suite);
        report.setOwner(Registrants.getCurrent());
        report.setAnswers(new HashMap<>());

        QuestionsSuiteRunnerTaisView view = getView(QuestionsSuiteRunnerTaisView.class);
        view.setRemainingQuestions(suite.getQuestions().size());
        view.setProgress(0);
        view.setQuestion(suite.getQuestions().get(0)); //todo validate questions count while suite creating
    }
}
