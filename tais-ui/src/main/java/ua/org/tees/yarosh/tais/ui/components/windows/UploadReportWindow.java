package ua.org.tees.yarosh.tais.ui.components.windows;

import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import ua.org.tees.yarosh.tais.homework.api.HomeworkResolver;
import ua.org.tees.yarosh.tais.homework.models.ManualTask;
import ua.org.tees.yarosh.tais.homework.models.ManualTaskReport;
import ua.org.tees.yarosh.tais.ui.components.PayloadReceiver;
import ua.org.tees.yarosh.tais.ui.core.api.AbstractWindow;
import ua.org.tees.yarosh.tais.ui.core.api.Registrants;
import ua.org.tees.yarosh.tais.ui.core.api.TaisWindow;

import static com.vaadin.ui.Button.ClickEvent;
import static ua.org.tees.yarosh.tais.ui.core.api.DataBinds.FS.REPORT_PAYLOAD_DIR;

@TaisWindow("Загрузка отчета")
public class UploadReportWindow extends AbstractWindow {

    private static final String PAYLOAD_NOT_UPLOADED = "Загрузите отчет";
    private HomeworkResolver homeworkResolver;

    private Upload payloadUploader = new Upload();
    private Label description = new Label();
    private Label theme = new Label();
    private Label discipline = new Label();
    private Label deadline = new Label();
    private Button save = new Button();

    private String filepath;
    private ManualTask manualTask;

    @Autowired
    public void setHomeworkResolver(HomeworkResolver homeworkResolver) {
        this.homeworkResolver = homeworkResolver;
    }

    public void setManualTask(ManualTask manualTask) {
        this.manualTask = manualTask;
    }

    @Override
    public void afterPropertiesSet() {
        description.setValue(manualTask.getDescription());
        theme.setValue(manualTask.getTheme());
        discipline.setValue(manualTask.getDiscipline().toString());
        deadline.setValue(manualTask.getDeadline().toString());
    }

    @Override
    public void init() {
        super.init();

        PayloadReceiver receiver = new PayloadReceiver(REPORT_PAYLOAD_DIR, e -> filepath = e.getFilename());
        payloadUploader.setReceiver(receiver);
        payloadUploader.addSucceededListener(receiver);
        payloadUploader.addFailedListener(receiver);

        save.addClickListener(this::saveReport);
    }

    private void saveReport(ClickEvent event) {
        if (filepath != null) {
            ManualTaskReport manualTaskReport = new ManualTaskReport();
            manualTaskReport.setFilePath(filepath);
            manualTaskReport.setOwner(Registrants.getCurrent());
            manualTaskReport.setTask(manualTask);
            homeworkResolver.resolve(manualTaskReport);
            close();
        } else {
            Notification.show(PAYLOAD_NOT_UPLOADED);
        }
    }

    @Override
    protected void fillOutLayout(VerticalLayout contentLayout) {
        contentLayout.addComponent(discipline);
        contentLayout.addComponent(theme);
        contentLayout.addComponent(description);
        contentLayout.addComponent(deadline);
        contentLayout.addComponent(payloadUploader);
        contentLayout.addComponent(save);
    }
}
