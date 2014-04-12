package ua.org.tees.yarosh.tais.ui.views.teacher.presenters;

import com.vaadin.ui.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import ua.org.tees.yarosh.tais.core.user.mgmt.api.service.RegistrantService;
import ua.org.tees.yarosh.tais.homework.api.HomeworkManager;
import ua.org.tees.yarosh.tais.homework.models.ManualTask;
import ua.org.tees.yarosh.tais.schedule.api.DisciplineService;
import ua.org.tees.yarosh.tais.ui.core.SessionFactory;
import ua.org.tees.yarosh.tais.ui.core.api.Updateable;
import ua.org.tees.yarosh.tais.ui.core.mvp.AbstractPresenter;
import ua.org.tees.yarosh.tais.ui.core.mvp.TaisPresenter;
import ua.org.tees.yarosh.tais.ui.views.teacher.CreateManualTaskView;
import ua.org.tees.yarosh.tais.ui.views.teacher.api.CreateManualTaskTaisView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.FileSystems;

import static com.vaadin.ui.Upload.*;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static ua.org.tees.yarosh.tais.ui.core.DataBinds.FS.HOME_DIR;
import static ua.org.tees.yarosh.tais.ui.core.DataBinds.FS.MANUAL_PAYLOAD_DIR;
import static ua.org.tees.yarosh.tais.ui.core.DataBinds.UriFragments.Teacher.ADD_MANUAL;
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
        getView(CreateManualTaskTaisView.class).setPayloadReceiver(new PayloadReceiver()); //fixme
    }

    @Override
    public void onCreate(ManualTask manualTask) {
        homeworkManager.createManualTask(manualTask);
    }

    public static class PayloadReceiver implements Receiver, SucceededListener, FailedListener {

        private String payloadPath;

        @Override
        public OutputStream receiveUpload(String filename, String mimeType) {
            log.debug("Trying to upload [{}] with mime [{}]", filename, mimeType);
            if (filename == null) {
                log.error("Filename is null");
                return null;
            }
            File parent = FileSystems.getDefault().getPath(HOME_DIR, MANUAL_PAYLOAD_DIR).toFile();
            if (!parent.exists() && !parent.mkdir()) {
                log.error("Can't create [{}] directory, manual task payload can't be persisted", parent.getPath());
            }
            File storingFile = createPayloadFile(filename);
            try {
                if (!storingFile.createNewFile()) {
                    storingFile = createPayloadFile(filename);
                }
                payloadPath = storingFile.getPath();
                return new FileOutputStream(storingFile);
            } catch (IOException e) {
                log.error("Can't store task payload. [REASON: {}]", e);
            }
            return null;
        }

        private File createPayloadFile(String filename) {
            File storingFile;
            storingFile = FileSystems.getDefault().getPath(HOME_DIR, MANUAL_PAYLOAD_DIR,
                    randomAlphanumeric(6).concat(filename)).toFile();
            log.debug("Generated path [{}]", storingFile);
            return storingFile;
        }

        @Override
        public void uploadSucceeded(SucceededEvent event) {
            SessionFactory.getCurrent().getView(CreateManualTaskView.class).setPayloadPath(payloadPath);
            Notification.show("Задание успешно загружено", Notification.Type.HUMANIZED_MESSAGE);
        }

        @Override
        public void uploadFailed(FailedEvent event) {
            log.error("Payload uploading failure. [REASON: {}]", event.getReason());
            Notification.show("Ошибка загрузки, вы выбрали файл?");
        }
    }
}
