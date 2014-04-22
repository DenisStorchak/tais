package ua.org.tees.yarosh.tais.ui.components.windows;

import com.vaadin.ui.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ua.org.tees.yarosh.tais.core.common.models.Discipline;
import ua.org.tees.yarosh.tais.core.common.models.StudentGroup;
import ua.org.tees.yarosh.tais.core.user.mgmt.api.service.RegistrantService;
import ua.org.tees.yarosh.tais.homework.api.HomeworkManager;
import ua.org.tees.yarosh.tais.homework.models.ManualTask;
import ua.org.tees.yarosh.tais.schedule.api.DisciplineService;
import ua.org.tees.yarosh.tais.ui.components.PayloadReceiver;
import ua.org.tees.yarosh.tais.ui.core.api.AbstractWindow;
import ua.org.tees.yarosh.tais.ui.core.api.Registrants;
import ua.org.tees.yarosh.tais.ui.core.api.TaisWindow;

import java.io.File;
import java.util.Date;

import static com.vaadin.server.Sizeable.Unit.PIXELS;
import static com.vaadin.ui.Notification.Type.TRAY_NOTIFICATION;
import static com.vaadin.ui.Notification.Type.WARNING_MESSAGE;
import static com.vaadin.ui.Notification.show;
import static ua.org.tees.yarosh.tais.ui.core.VaadinUtils.*;
import static ua.org.tees.yarosh.tais.ui.core.api.DataBinds.FS.TASK_PAYLOAD_DIR;

/**
 * @author Timur Yarosh
 *         Date: 18.04.14
 *         Time: 20:38
 */
@TaisWindow("Новое задание")
public class CreateManualTaskWindow extends AbstractWindow {

    public static final Logger log = LoggerFactory.getLogger(CreateManualTaskWindow.class);

    private static final String DISCIPLINE_TITLE = "Дисциплина";
    private static final String STUDENT_GROUP_TITLE = "Группа";
    private static final String DEADLINE_TITLE = "Дедлайн";
    private static final String DESCRIPTION_TITLE = "Описание";
    private static final String THEME_TITLE = "Тема";
    private static final String ERR_MESSAGE = "Заполните все поля";
    private static final String ADD_TASK_CAPTION = "Добавить задание";
    private static final String SAVING_MESSAGE = "Сохранение...";
    private TextArea description = new TextArea();
    private TextField theme = new TextField();
    private ComboBox disciplines = new ComboBox();
    private ComboBox studentGroups = new ComboBox();
    private Upload payload = new Upload();
    private DateField deadline = new DateField();

    private RegistrantService registrantService;
    private HomeworkManager homeworkManager;
    private DisciplineService disciplineService;

    @Autowired
    public void setRegistrantService(RegistrantService registrantService) {
        this.registrantService = registrantService;
    }

    @Autowired
    public void setHomeworkManager(HomeworkManager homeworkManager) {
        this.homeworkManager = homeworkManager;
    }

    @Autowired
    public void setDisciplineService(DisciplineService disciplineService) {
        this.disciplineService = disciplineService;
    }

    @Override
    public void init() {
        super.init();

        PayloadReceiver payloadReceiver = new PayloadReceiver(TASK_PAYLOAD_DIR, this::saveManualTask);
        payload.setReceiver(payloadReceiver);
        payload.addSucceededListener(payloadReceiver);
        payload.addFailedListener(payloadReceiver);
        payload.setImmediate(true);
        payload.setButtonCaption(ADD_TASK_CAPTION);
        payload.addStyleName("default");

        disciplineService.findAllDisciplines().forEach(disciplines::addItem);
        registrantService.findAllStudentGroups().forEach(studentGroups::addItem);

        description.setWidth(500, PIXELS);
    }

    public void saveManualTask(File payload) {
        if (isValid(description, theme, disciplines, studentGroups, deadline) && payload.exists()) {
            log.info("new manual task saving...");
            show(SAVING_MESSAGE, TRAY_NOTIFICATION);
            ManualTask manualTask = new ManualTask();
            manualTask.setDescription(description.getValue());
            manualTask.setTheme(theme.getValue());
            manualTask.setDiscipline((Discipline) disciplines.getValue());
            manualTask.setStudentGroup((StudentGroup) studentGroups.getValue());
            manualTask.setEnabled(true);
            manualTask.setPayloadPath(payload.getPath());
            manualTask.setDeadline(deadline.getValue());
            manualTask.setExaminer(Registrants.getCurrent());
            manualTask.setTimestamp(new Date());

            homeworkManager.createManualTask(manualTask);
            close();
        } else {
            show(ERR_MESSAGE, WARNING_MESSAGE);
        }
    }

    @Override
    protected void fillOutLayout(VerticalLayout contentLayout) {
        contentLayout.addComponent(createSingleFormLayout(new Label(DISCIPLINE_TITLE), disciplines));
        contentLayout.addComponent(createSingleFormLayout(new Label(STUDENT_GROUP_TITLE), studentGroups));
        contentLayout.addComponent(createSingleFormLayout(new Label(DEADLINE_TITLE), deadline));
        contentLayout.addComponent(createSingleFormLayout(new Label(THEME_TITLE), theme));
        contentLayout.addComponent(createSingleFormVerticalLayout(new Label(DESCRIPTION_TITLE), description));
        contentLayout.addComponent(payload);
        contentLayout.setComponentAlignment(payload, Alignment.MIDDLE_RIGHT);
    }
}
