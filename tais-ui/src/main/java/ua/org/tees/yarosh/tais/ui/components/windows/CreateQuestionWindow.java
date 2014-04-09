package ua.org.tees.yarosh.tais.ui.components.windows;

import com.vaadin.ui.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import ua.org.tees.yarosh.tais.homework.models.Answer;
import ua.org.tees.yarosh.tais.homework.models.Question;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.vaadin.event.ShortcutAction.KeyCode.ENTER;
import static com.vaadin.event.ShortcutAction.KeyCode.ESCAPE;
import static ua.org.tees.yarosh.tais.ui.views.teacher.api.CreateQuestionsSuiteTais.CreateQuestionsSuitePresenter;

@Service
@Scope("prototype")
@SuppressWarnings("unchecked")
public class CreateQuestionWindow extends Window {

    private CreateQuestionWindow window;

    private Question question;
    private CreateQuestionsSuitePresenter presenter;

    public CreateQuestionWindow() {
        super("Создать вопрос");
        window = this;
        setModal(true);
        setClosable(true);
        setResizable(false);
        addStyleName("edit-dashboard");
        setContent(new CreateQuestionWindowContent());
    }

    public void setPresenter(CreateQuestionsSuitePresenter presenter) {
        this.presenter = presenter;
    }

    public CreateQuestionsSuitePresenter getPresenter() {
        return presenter;
    }

    public class CreateQuestionWindowContent extends VerticalLayout {

        private TextArea description = new TextArea("Вопрос");
        private Map<TextArea, CheckBox> forms = new LinkedHashMap() {{
            put(new TextArea("Ответ 1"), new CheckBox("Правильный ответ"));
            put(new TextArea("Ответ 2"), new CheckBox("Правильный ответ"));
            put(new TextArea("Ответ 3"), new CheckBox("Правильный ответ"));
            put(new TextArea("Ответ 4"), new CheckBox("Правильный ответ"));
        }};
        private Button add = new Button("Добавить");

        public CreateQuestionWindowContent() {
            setCloseShortcut(ESCAPE);
            addComponent(new HorizontalLayout() {
                {
                    setMargin(true);
                    setSpacing(true);
                    addStyleName("footer");
                    setWidth("100%");
                    addComponent(description);
                    description.setWidth(100, Unit.PERCENTAGE);
                }
            });
            forms.entrySet().stream().forEach(e -> addComponent(createAnswerLayout(e.getKey(), e.getValue())));
            addComponent(add);
            setComponentAlignment(add, Alignment.BOTTOM_RIGHT);
            add.setClickShortcut(ENTER);
            add.addStyleName("default");
            add.addClickListener(event -> {
                question = new Question();
                question.setDescription(description.getValue());
                question.setAnswers(new ArrayList<>());
                forms.entrySet().stream().forEach(e -> question.getAnswers()
                        .add(new Answer(question, e.getKey().getValue(), e.getValue().getValue())));
                presenter.addQuestion(question);
                presenter.update();
                window.close();
            });
        }

        private VerticalLayout createAnswerLayout(Component answer, Component truth) {
            return new VerticalLayout() {
                {
                    setMargin(true);
                    setSpacing(true);
                    addStyleName("footer");
                    setWidth("100%");
                    addComponents(answer, truth);
                    setComponentAlignment(answer, Alignment.TOP_CENTER);
                    setComponentAlignment(truth, Alignment.BOTTOM_RIGHT);
                    answer.setWidth(500, Unit.PIXELS);
                }
            };
        }
    }
}
