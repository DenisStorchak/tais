package ua.org.tees.yarosh.tais.ui.views.teacher.presenters;

import com.vaadin.ui.UI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import ua.org.tees.yarosh.tais.core.common.models.StudentGroup;
import ua.org.tees.yarosh.tais.core.user.mgmt.api.service.RegistrantService;
import ua.org.tees.yarosh.tais.homework.api.HomeworkManager;
import ua.org.tees.yarosh.tais.homework.models.QuestionsSuite;
import ua.org.tees.yarosh.tais.ui.components.windows.QuestionSuiteDetailsWindow;
import ua.org.tees.yarosh.tais.ui.core.api.Updateable;
import ua.org.tees.yarosh.tais.ui.core.mvp.AbstractPresenter;
import ua.org.tees.yarosh.tais.ui.core.mvp.TaisPresenter;
import ua.org.tees.yarosh.tais.ui.views.teacher.api.EnabledQuestionsSuitesTaisView;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static ua.org.tees.yarosh.tais.ui.core.api.DataBinds.UriFragments.Teacher.ENABLED_QUESTIONS;

/**
 * @author Timur Yarosh
 *         Date: 08.04.14
 *         Time: 21:29
 */
@TaisPresenter
public class EnabledQuestionsSuitesListener extends AbstractPresenter implements EnabledQuestionsSuitesTaisView.EnabledQuestionsSuitesPresenter {

    private HomeworkManager homeworkManager;
    private RegistrantService registrantService;

    @Autowired
    public void setHomeworkManager(HomeworkManager homeworkManager) {
        this.homeworkManager = homeworkManager;
    }

    @Autowired
    public void setRegistrantService(RegistrantService registrantService) {
        this.registrantService = registrantService;
    }

    @Autowired
    public EnabledQuestionsSuitesListener(@Qualifier(ENABLED_QUESTIONS) Updateable view) {
        super(view);
    }

    @Override
    public void init() {
        getView(EnabledQuestionsSuitesTaisView.class).setGroups(registrantService.findAllStudentGroups());
    }

    @Override
    public void onDelete(long id, StudentGroup studentGroup) {
        homeworkManager.disableQuestionsSuite(id);
        onSearch(studentGroup);
    }

    @Override
    public void onDetails(long id) {
        UI.getCurrent().addWindow(new QuestionSuiteDetailsWindow(homeworkManager.findQuestionsSuite(id)));
    }

    @Override
    public void onSearch(StudentGroup studentGroup) {
        List<QuestionsSuite> enabledSuites = homeworkManager.findQuestionsSuites(studentGroup)
                .stream().filter(QuestionsSuite::getEnabled).collect(toList());
        getView(EnabledQuestionsSuitesTaisView.class).setSuites(enabledSuites);
    }
}
