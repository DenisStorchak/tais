package ua.org.tees.yarosh.tais.ui.views.teacher.presenters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import ua.org.tees.yarosh.tais.core.common.models.StudentGroup;
import ua.org.tees.yarosh.tais.core.user.mgmt.api.service.RegistrantService;
import ua.org.tees.yarosh.tais.homework.api.HomeworkManager;
import ua.org.tees.yarosh.tais.ui.core.api.UpdatableView;
import ua.org.tees.yarosh.tais.ui.core.mvp.AbstractPresenter;
import ua.org.tees.yarosh.tais.ui.core.mvp.TaisPresenter;
import ua.org.tees.yarosh.tais.ui.views.teacher.api.EnabledQuestionsSuiteTaisView;

import static ua.org.tees.yarosh.tais.ui.core.DataBinds.UriFragments.Teacher.ENABLED_QUESTIONS;
import static ua.org.tees.yarosh.tais.ui.views.teacher.api.EnabledQuestionsSuiteTaisView.EnabledQuestionsSuitePresenter;

/**
 * @author Timur Yarosh
 *         Date: 08.04.14
 *         Time: 21:29
 */
@TaisPresenter
public class EnabledQuestionsSuiteListener extends AbstractPresenter implements EnabledQuestionsSuitePresenter {

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
    public EnabledQuestionsSuiteListener(@Qualifier(ENABLED_QUESTIONS) UpdatableView view) {
        super(view);
    }

    @Override
    public void init() {
        getView(EnabledQuestionsSuiteTaisView.class).setGroups(registrantService.findAllStudentGroups());
    }

    @Override
    public void onDelete(long id) {

    }

    @Override
    public void onDetails(long id) {

    }

    @Override
    public void onSearch(StudentGroup studentGroup) {
        getView(EnabledQuestionsSuiteTaisView.class).setSuites(homeworkManager.findQuestionsSuites(studentGroup));
    }
}
