package ua.org.tees.yarosh.tais.ui.views.teacher.presenters;

import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import ua.org.tees.yarosh.tais.core.common.models.Discipline;
import ua.org.tees.yarosh.tais.core.common.models.StudentGroup;
import ua.org.tees.yarosh.tais.core.user.mgmt.api.service.RegistrantService;
import ua.org.tees.yarosh.tais.homework.api.HomeworkManager;
import ua.org.tees.yarosh.tais.homework.models.Question;
import ua.org.tees.yarosh.tais.homework.models.QuestionsSuite;
import ua.org.tees.yarosh.tais.schedule.api.DisciplineService;
import ua.org.tees.yarosh.tais.ui.core.Registrants;
import ua.org.tees.yarosh.tais.ui.core.api.Updateable;
import ua.org.tees.yarosh.tais.ui.core.mvp.AbstractPresenter;
import ua.org.tees.yarosh.tais.ui.core.mvp.TaisPresenter;
import ua.org.tees.yarosh.tais.ui.views.teacher.CreateQuestionsSuiteView;

import java.util.List;

import static com.vaadin.ui.Notification.Type.ERROR_MESSAGE;
import static com.vaadin.ui.Notification.show;
import static ua.org.tees.yarosh.tais.ui.core.DataBinds.UriFragments.Teacher.CREATE_QUESTIONS_SUITE;
import static ua.org.tees.yarosh.tais.ui.core.VaadinUtils.isValid;
import static ua.org.tees.yarosh.tais.ui.views.teacher.api.CreateQuestionsSuiteTaisView.CreateQuestionsSuitePresenter;

@TaisPresenter
public class CreateQuestionsSuiteListener extends AbstractPresenter implements CreateQuestionsSuitePresenter {

    private HomeworkManager homeworkManager;
    private RegistrantService registrantService;
    private DisciplineService disciplineService;

    @Autowired
    public void setHomeworkManager(HomeworkManager homeworkManager) {
        this.homeworkManager = homeworkManager;
    }

    @Autowired
    public void setRegistrantService(RegistrantService registrantService) {
        this.registrantService = registrantService;
    }

    @Autowired
    public void setDisciplineService(DisciplineService disciplineService) {
        this.disciplineService = disciplineService;
    }

    @Autowired
    public CreateQuestionsSuiteListener(@Qualifier(CREATE_QUESTIONS_SUITE) Updateable view) {
        super(view);
    }

    @Override
    public boolean createQuestionsSuite(ComboBox studentGroup,
                                        TextField theme,
                                        ComboBox discipline,
                                        List<Question> questions,
                                        DateField deadline,
                                        ComboBox enabled) {
        if (!isValid(studentGroup, theme, discipline, deadline, enabled)) {
            show("Неправильно заполнено поле", ERROR_MESSAGE);
            return false;
        } else {
            QuestionsSuite suite = new QuestionsSuite();
            suite.setStudentGroup((StudentGroup) studentGroup.getValue());
            suite.setTheme(theme.getValue());
            suite.setDiscipline((Discipline) discipline.getValue());
            suite.setQuestions(questions);
            suite.setDeadline(deadline.getValue());
            suite.setEnabled(enabled.getValue().equals("Включен"));
            suite.setExaminer(Registrants.getCurrent());

            homeworkManager.createQuestionsSuite(suite);
            return true;
        }
    }

    @Override
    public List<StudentGroup> studentGroups() {
        return registrantService.findAllStudentGroups();
    }

    @Override
    public List<Discipline> disciplines() {
        return disciplineService.findAllDisciplines();
    }

    @Override
    public void addQuestion(Question question) {
        getView(CreateQuestionsSuiteView.class).questions().add(question);
    }
}
