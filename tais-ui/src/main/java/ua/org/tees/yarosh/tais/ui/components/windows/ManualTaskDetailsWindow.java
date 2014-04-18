package ua.org.tees.yarosh.tais.ui.components.windows;

import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import ua.org.tees.yarosh.tais.homework.models.ManualTask;
import ua.org.tees.yarosh.tais.ui.core.api.AbstractWindow;
import ua.org.tees.yarosh.tais.ui.core.api.TaisWindow;

import java.io.File;

import static ua.org.tees.yarosh.tais.ui.core.VaadinUtils.createSingleFormLayout;
import static ua.org.tees.yarosh.tais.ui.core.VaadinUtils.extendDownloadButton;

@TaisWindow("Задание")
public class ManualTaskDetailsWindow extends AbstractWindow {
    private static final String PAYLOAD_NOT_UPLOADED = "Загрузите отчет";
    private static final String DISCIPLINE_TITLE = "Дисциплина";
    private static final String THEME_TITLE = "Тема";
    private static final String DESCRIPTION_TITLE = "Описание";
    private static final String DEADLINE_TITLE = "Дедлайн";

    private Label description = new Label();
    private Label theme = new Label();
    private Label discipline = new Label();
    private Label deadline = new Label();
    private Label examiner = new Label();
    private Button download = new Button("Скачать");

    private ManualTask manualTask;

    public void setManualTask(ManualTask manualTask) {
        this.manualTask = manualTask;
    }

    @Override
    public void afterPropertiesSet() {
        description.setValue(manualTask.getDescription());
        theme.setValue(manualTask.getTheme());
        discipline.setValue(manualTask.getDiscipline().toString());
        deadline.setValue(manualTask.getDeadline().toString());
        examiner.setValue(manualTask.getExaminer().toString());
    }

    @Override
    public void init() {
        super.init();
        extendDownloadButton(new File(manualTask.getPayloadPath()), download);
    }

    @Override
    protected void fillOutLayout(VerticalLayout contentLayout) {
        contentLayout.addComponent(createSingleFormLayout(new Label(DISCIPLINE_TITLE), discipline));
        contentLayout.addComponent(createSingleFormLayout(new Label(THEME_TITLE), theme));
        contentLayout.addComponent(createSingleFormLayout(new Label(DESCRIPTION_TITLE), description));
        contentLayout.addComponent(createSingleFormLayout(new Label(DEADLINE_TITLE), deadline));
        contentLayout.addComponent(download);
    }
}
