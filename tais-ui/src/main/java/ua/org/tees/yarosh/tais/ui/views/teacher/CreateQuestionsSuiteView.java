package ua.org.tees.yarosh.tais.ui.views.teacher;

import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Qualifier;
import ua.org.tees.yarosh.tais.auth.annotations.PermitRoles;
import ua.org.tees.yarosh.tais.homework.models.Question;
import ua.org.tees.yarosh.tais.ui.components.layouts.DashPanel;
import ua.org.tees.yarosh.tais.ui.components.layouts.DashboardView;
import ua.org.tees.yarosh.tais.ui.components.windows.CreateQuestionWindow;
import ua.org.tees.yarosh.tais.ui.core.SessionFactory;
import ua.org.tees.yarosh.tais.ui.core.mvp.PresentedBy;
import ua.org.tees.yarosh.tais.ui.core.mvp.TaisView;
import ua.org.tees.yarosh.tais.ui.core.validators.NotBlankValidator;
import ua.org.tees.yarosh.tais.ui.core.validators.NotNullValidator;
import ua.org.tees.yarosh.tais.ui.views.teacher.api.CreateQuestionsSuiteTaisView;

import java.util.ArrayList;
import java.util.List;

import static com.vaadin.event.ShortcutAction.KeyCode.ENTER;
import static com.vaadin.event.ShortcutAction.ModifierKey.CTRL;
import static java.lang.String.format;
import static ua.org.tees.yarosh.tais.core.common.dto.Roles.TEACHER;
import static ua.org.tees.yarosh.tais.ui.core.DataBinds.UriFragments.Teacher.CREATE_QUESTIONS_SUITE;
import static ua.org.tees.yarosh.tais.ui.core.SessionFactory.getCurrent;
import static ua.org.tees.yarosh.tais.ui.core.VaadinUtils.createSingleFormLayout;
import static ua.org.tees.yarosh.tais.ui.views.teacher.api.CreateQuestionsSuiteTaisView.CreateQuestionsSuitePresenter;

@TaisView("Создать тест")
@PresentedBy(CreateQuestionsSuitePresenter.class)
@PermitRoles({TEACHER})
@Qualifier(CREATE_QUESTIONS_SUITE)
public class CreateQuestionsSuiteView extends DashboardView implements CreateQuestionsSuiteTaisView {

    private final HorizontalLayout controls;
    private ComboBox studentGroup = new ComboBox();
    private TextField theme = new TextField();
    private ComboBox discipline = new ComboBox();
    private DateField deadline = new DateField();
    private ComboBox enabled = new ComboBox();
    private Button addQuestion = new Button("Добавить вопрос");
    private Button saveSuite = new Button("Сохранить тест");

    private List<Question> questions = new ArrayList<>();
    private final DashPanel dashPanel;

    public CreateQuestionsSuiteView() {
        setUpValidators();
        controls = createSingleFormLayout(addQuestion, saveSuite);
        dashPanel = addDashPanel(null, null,
                createSingleFormLayout(new Label("Группа"), studentGroup),
                createSingleFormLayout(new Label("Дисциплина"), discipline),
                createSingleFormLayout(new Label("Статус"), enabled),
                createSingleFormLayout(new Label("Тема"), theme),
                createSingleFormLayout(new Label("Дедлайн"), deadline),
                controls);
        dashPanel.setSizeUndefined();
        dashPanel.setWidth(50, Unit.PERCENTAGE);
        dash.setComponentAlignment(dashPanel, Alignment.MIDDLE_CENTER);

        addQuestion.setClickShortcut(CTRL, ENTER);
        addQuestion.addClickListener(event -> {
            CreateQuestionWindow window = SessionFactory.getCurrent().getWindow(CreateQuestionWindow.class);
            getUI().addWindow(window);
            window.setPresenter(SessionFactory.getCurrent().getRelativePresenter(this, CreateQuestionsSuitePresenter.class));
        });
        saveSuite.setClickShortcut(ENTER);
        saveSuite.addStyleName("default");
        saveSuite.addClickListener(event -> {
            if (getCurrent().getRelativePresenter(this, CreateQuestionsSuitePresenter.class)
                    .createQuestionsSuite(studentGroup, theme, discipline, questions, deadline, enabled)) {
                theme.setValue("");
                questions = new ArrayList<>();
                update();
            }
        });
        enabled.addItem("Включен");
        enabled.addItem("Выключен");
        enabled.setValue("Включен");
    }

    private void setUpValidators() {
        studentGroup.addValidator(new NotNullValidator("Выберите группу"));
        studentGroup.setValidationVisible(false);
        theme.addValidator(new NotBlankValidator("Тема должна быть заполнена"));
        theme.setValidationVisible(false);
        discipline.addValidator(new NotNullValidator("Выберите дисциплину"));
        discipline.setValidationVisible(false);
        deadline.addValidator(new NotNullValidator("Выберите дату дедлайна"));
        deadline.setValidationVisible(false);
        enabled.addValidator(new NotNullValidator("Статус"));
        enabled.setValidationVisible(false);
    }

    @Override
    public void update() {
        CreateQuestionsSuitePresenter presenter = getCurrent()
                .getRelativePresenter(this, CreateQuestionsSuitePresenter.class);

        studentGroup.removeAllItems();
        presenter.studentGroups().forEach(studentGroup::addItem);

        discipline.removeAllItems();
        presenter.disciplines().forEach(discipline::addItem);

        controls.setCaption(format("Создано [%s] новых вопросов", questions.size()));
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        update();
    }

    @Override
    public List<Question> questions() {
        return questions;
    }
}
