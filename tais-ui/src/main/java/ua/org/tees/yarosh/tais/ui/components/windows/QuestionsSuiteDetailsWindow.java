package ua.org.tees.yarosh.tais.ui.components.windows;

import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import ua.org.tees.yarosh.tais.homework.models.QuestionsSuite;
import ua.org.tees.yarosh.tais.ui.core.api.AbstractWindow;
import ua.org.tees.yarosh.tais.ui.core.api.TaisWindow;

import static ua.org.tees.yarosh.tais.ui.core.VaadinUtils.createSingleFormLayout;

@TaisWindow("Тест")
public class QuestionsSuiteDetailsWindow extends AbstractWindow {

    private static final String STUDENT_GROUP_TITLE = "Группа";
    private static final String THEME_TITLE = "Тема";
    private static final String DISCIPLINE_TITLE = "Дисциплина";
    private static final String DEADLINE_TITLE = "Дедлайн";
    private static final String QUESTIONS_COUNT_TITLE = "Количество вопросов";
    private static final String EXAMINER_TITLE = "Автор";
    private QuestionsSuite questionsSuite;

    private Label studentGroup = new Label();
    private Label theme = new Label();
    private Label discipline = new Label();
    private Label deadline = new Label();
    private Label examiner = new Label();
    private Label questionsCount = new Label();

    public void setQuestionsSuite(QuestionsSuite questionsSuite) {
        this.questionsSuite = questionsSuite;
    }

    @Override
    public void afterPropertiesSet() {
        studentGroup.setValue(questionsSuite.getStudentGroup().toString());
        theme.setValue(questionsSuite.getTheme());
        discipline.setValue(questionsSuite.getDiscipline().toString());
        deadline.setValue(questionsSuite.getDeadline().toString());
        examiner.setValue(questionsSuite.getExaminer().toString());
        questionsCount.setValue(String.valueOf(questionsSuite.getQuestions().size()));
    }

    @Override
    protected void fillOutLayout(VerticalLayout contentLayout) {
        contentLayout.addComponent(createSingleFormLayout(new Label(STUDENT_GROUP_TITLE), studentGroup));
        contentLayout.addComponent(createSingleFormLayout(new Label(THEME_TITLE), theme));
        contentLayout.addComponent(createSingleFormLayout(new Label(DISCIPLINE_TITLE), discipline));
        contentLayout.addComponent(createSingleFormLayout(new Label(DEADLINE_TITLE), deadline));
        contentLayout.addComponent(createSingleFormLayout(new Label(EXAMINER_TITLE), examiner));
        contentLayout.addComponent(createSingleFormLayout(new Label(QUESTIONS_COUNT_TITLE), questionsCount));
    }
}
