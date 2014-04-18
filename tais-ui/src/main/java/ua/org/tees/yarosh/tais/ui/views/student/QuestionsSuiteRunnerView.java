package ua.org.tees.yarosh.tais.ui.views.student;

import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Qualifier;
import ua.org.tees.yarosh.tais.auth.api.annotations.PermitRoles;
import ua.org.tees.yarosh.tais.homework.models.Answer;
import ua.org.tees.yarosh.tais.homework.models.Question;
import ua.org.tees.yarosh.tais.ui.components.layouts.DashPanel;
import ua.org.tees.yarosh.tais.ui.components.layouts.DashboardView;
import ua.org.tees.yarosh.tais.ui.core.mvp.PresentedBy;
import ua.org.tees.yarosh.tais.ui.core.mvp.TaisView;
import ua.org.tees.yarosh.tais.ui.views.student.api.QuestionsSuiteRunnerTaisView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.vaadin.event.ShortcutAction.KeyCode.ENTER;
import static java.lang.String.format;
import static ua.org.tees.yarosh.tais.core.common.dto.Roles.STUDENT;
import static ua.org.tees.yarosh.tais.ui.core.UIFactoryAccessor.getCurrent;
import static ua.org.tees.yarosh.tais.ui.core.api.DataBinds.UriFragments.Student.QUESTIONS_RUNNER;
import static ua.org.tees.yarosh.tais.ui.views.student.api.QuestionsSuiteRunnerTaisView.QuestionsSuiteRunnerPresenter;

/**
 * @author Timur Yarosh
 *         Date: 12.04.14
 *         Time: 11:41
 */
@PresentedBy(QuestionsSuiteRunnerPresenter.class)
@Qualifier(QUESTIONS_RUNNER)
@PermitRoles(STUDENT)
@TaisView("Тестирование")
public class QuestionsSuiteRunnerView extends DashboardView implements QuestionsSuiteRunnerTaisView {

    private ProgressBar progressBar = new ProgressBar(0.0f);
    private Label remaining = new Label();
    private Label question = new Label();
    private Map<Label, CheckBox> uiAnswers = new HashMap<>();
    private Button giveAnswer = new Button("Ответить");

    private List<Answer> answers = new ArrayList<>();

    public QuestionsSuiteRunnerView() {
        super(false);
        top.addComponents(progressBar, remaining);
    }

    @Override
    public void init() {
        super.init();
        QuestionsSuiteRunnerPresenter p = getCurrent().getRelativePresenter(this, QuestionsSuiteRunnerPresenter.class);
        giveAnswer.setClickShortcut(ENTER);
        giveAnswer.addClickListener(e -> {
            List<Answer> answered = new ArrayList<>();
            uiAnswers.entrySet().stream().filter(a -> a.getValue().getValue())
                    .forEach(a -> answered.add(findAnswer(a.getKey().getValue())));

            p.onAnswered(answered);
        });
    }

    private Answer findAnswer(String value) {
        return answers.stream().filter(a -> a.getText().equals(value)).findFirst().get();
    }

    @Override
    public void setQuestion(Question question) {
        this.question.setValue(question.getDescription());
        uiAnswers.clear();
        for (Answer answer : question.getAnswers()) {
            uiAnswers.put(new Label(answer.getText()), new CheckBox("Правильный ответ"));
            answers.add(answer);
        }
        refreshDash(uiAnswers);
    }

    private void refreshDash(Map<Label, CheckBox> answers) {
        dash.removeAllComponents();
        DashPanel dashPanel = addDashPanel(null, null, question);
        for (Label answer : answers.keySet()) {
            dashPanel.addComponent(new HorizontalLayout(answer, answers.get(answer)));
        }
        dashPanel.addComponent(giveAnswer);
    }

    @Override
    public void setRemainingQuestions(int remaining) {
        this.remaining.setValue(format("Осталось вопросов: %d", remaining));
    }

    @Override
    public void setProgress(float progress) {
        progressBar.setValue(progress);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
    }
}
