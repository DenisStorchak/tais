package ua.org.tees.yarosh.tais.ui.views.teacher.presenters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import ua.org.tees.yarosh.tais.core.user.mgmt.api.service.RegistrantService;
import ua.org.tees.yarosh.tais.homework.api.HomeworkManager;
import ua.org.tees.yarosh.tais.homework.models.ManualTask;
import ua.org.tees.yarosh.tais.schedule.api.DisciplineService;
import ua.org.tees.yarosh.tais.ui.components.PayloadReceiver;
import ua.org.tees.yarosh.tais.ui.core.UIFactoryAccessor;
import ua.org.tees.yarosh.tais.ui.core.api.Updateable;
import ua.org.tees.yarosh.tais.ui.core.mvp.AbstractPresenter;
import ua.org.tees.yarosh.tais.ui.core.mvp.TaisPresenter;
import ua.org.tees.yarosh.tais.ui.views.teacher.CreateManualTaskView;
import ua.org.tees.yarosh.tais.ui.views.teacher.api.CreateManualTaskTaisView;

import static ua.org.tees.yarosh.tais.ui.core.api.DataBinds.FS.TASK_PAYLOAD_DIR;
import static ua.org.tees.yarosh.tais.ui.core.api.DataBinds.UriFragments.Teacher.ADD_MANUAL;
import static ua.org.tees.yarosh.tais.ui.views.teacher.api.CreateManualTaskTaisView.CreateManualTaskPresenter;

@TaisPresenter
public class CreateManualTaskListener extends AbstractPresenter implements CreateManualTaskPresenter {

    private DisciplineService disciplineService;
    private RegistrantService registrantService;
    private HomeworkManager homeworkManager;

    @Autowired
    public void setDisciplineService(DisciplineService disciplineService) {
        this.disciplineService = disciplineService;
    }

    @Autowired
    public void setRegistrantService(RegistrantService registrantService) {
        this.registrantService = registrantService;
    }

    @Autowired
    public void setHomeworkManager(HomeworkManager homeworkManager) {
        this.homeworkManager = homeworkManager;
    }

    @Autowired
    public CreateManualTaskListener(@Qualifier(ADD_MANUAL) Updateable view) {
        super(view);
    }

    @Override
    public void init() {
        getView(CreateManualTaskTaisView.class).setDisciplines(disciplineService.findAllDisciplines());
        getView(CreateManualTaskTaisView.class).setGroups(registrantService.findAllStudentGroups());
        getView(CreateManualTaskTaisView.class).setPayloadReceiver(new PayloadReceiver(
                TASK_PAYLOAD_DIR,
                e -> UIFactoryAccessor.getCurrent().getView(CreateManualTaskView.class).setPayloadPath(e.getFilename())
        ));
    }

    @Override
    public void onCreate(ManualTask manualTask) {
        homeworkManager.createManualTask(manualTask);
    }
}
