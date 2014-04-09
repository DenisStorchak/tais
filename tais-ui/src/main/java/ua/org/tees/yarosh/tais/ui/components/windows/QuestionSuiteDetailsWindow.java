package ua.org.tees.yarosh.tais.ui.components.windows;

import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import ua.org.tees.yarosh.tais.homework.models.QuestionsSuite;

import java.text.SimpleDateFormat;

import static com.vaadin.event.ShortcutAction.KeyCode.ESCAPE;
import static ua.org.tees.yarosh.tais.ui.core.VaadinUtils.createSingleFormLayout;

public class QuestionSuiteDetailsWindow extends Window {

    public QuestionSuiteDetailsWindow(QuestionsSuite questionsSuite) {
        super("Детали теста");
        setModal(true);
        setClosable(true);
        setResizable(false);
        addStyleName("edit-dashboard");
        setContent(new QuestionSuiteDetailsWindowContent(questionsSuite));
    }

    private class QuestionSuiteDetailsWindowContent extends VerticalLayout {
        public QuestionSuiteDetailsWindowContent(QuestionsSuite questionsSuite) {
            addComponent(new VerticalLayout() {
                {
                    setMargin(true);
                    setSpacing(true);
                    addStyleName("footer");
                    setWidth("100%");
                    setCloseShortcut(ESCAPE);

                    addComponent(createSingleFormLayout(new Label("Группа"),
                            new Label(questionsSuite.getStudentGroup().toString())));
                    addComponent(createSingleFormLayout(new Label("Тема"),
                            new Label(questionsSuite.getTheme())));
                    addComponent(createSingleFormLayout(new Label("Дисциплина"),
                            new Label(questionsSuite.getDiscipline().toString())));
                    addComponent(createSingleFormLayout(new Label("Дедлайн"),
                            new Label(new SimpleDateFormat("dd.MM.yyyy").format(questionsSuite.getDeadline()))));
                    addComponent(createSingleFormLayout(new Label("Количество вопросов"),
                            new Label(String.valueOf(questionsSuite.getQuestions().size()))));
                }
            });
        }
    }
}
