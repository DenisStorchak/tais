package ua.org.tees.yarosh.tais.ui.roles.student.presenters;

import com.vaadin.server.VaadinSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.org.tees.yarosh.tais.core.common.exceptions.RegistrantNotFoundException;
import ua.org.tees.yarosh.tais.core.common.models.Registrant;
import ua.org.tees.yarosh.tais.core.user.mgmt.api.service.RegistrantService;
import ua.org.tees.yarosh.tais.homework.api.HomeworkManager;
import ua.org.tees.yarosh.tais.homework.models.ManualTask;
import ua.org.tees.yarosh.tais.homework.models.QuestionsSuite;
import ua.org.tees.yarosh.tais.ui.core.mvp.AbstractPresenter;
import ua.org.tees.yarosh.tais.ui.core.mvp.TaisView;
import ua.org.tees.yarosh.tais.ui.roles.student.api.PersonalWorktableView;

import java.util.List;

import static ua.org.tees.yarosh.tais.ui.core.SessionAttributes.REGISTRANT_ID;

@Service
public class PersonalWorktablePresenter extends AbstractPresenter implements PersonalWorktableView.PersonalWorktableListener {

    private RegistrantService registrantService;
    private HomeworkManager homeworkManager;

    public PersonalWorktablePresenter(TaisView view) {
        super(view);
    }

    @Autowired
    public void setRegistrantService(RegistrantService registrantService) {
        this.registrantService = registrantService;
    }

    @Autowired
    public void setHomeworkManager(HomeworkManager homeworkManager) {
        this.homeworkManager = homeworkManager;
    }

    @Override
    public List<ManualTask> listActualUnresolvedManualTasks() {
        String registrantId = (String) VaadinSession.getCurrent().getAttribute(REGISTRANT_ID);
        Registrant registrant;
        try {
            registrant = registrantService.getRegistration(registrantId);
            return homeworkManager.findUnresolvedActualManualTasks(registrant);
        } catch (RegistrantNotFoundException e) {
            info(e.getMessage());
        }
        return null;
    }

    @Override
    public List<QuestionsSuite> listActualQuestionsSuites() {
        String registrantId = (String) VaadinSession.getCurrent().getAttribute(REGISTRANT_ID);
        Registrant registrant;
        try {
            registrant = registrantService.getRegistration(registrantId);
            return homeworkManager.findUnresolvedActualQuestionsSuite(registrant);
        } catch (RegistrantNotFoundException e) {
            info(e.getMessage());
        }
        return null;
    }

    @Override
    protected void initView() {
        getView().addPresenter(this);
    }
}
