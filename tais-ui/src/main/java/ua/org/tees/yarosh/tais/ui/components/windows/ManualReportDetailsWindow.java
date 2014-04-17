package ua.org.tees.yarosh.tais.ui.components.windows;

import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import ua.org.tees.yarosh.tais.homework.api.HomeworkManager;
import ua.org.tees.yarosh.tais.homework.models.ManualTaskReport;
import ua.org.tees.yarosh.tais.ui.core.api.AbstractWindow;
import ua.org.tees.yarosh.tais.ui.core.api.Registrants;
import ua.org.tees.yarosh.tais.ui.core.api.TaisWindow;

import java.io.File;

import static ua.org.tees.yarosh.tais.ui.core.VaadinUtils.*;

@TaisWindow("Отчет")
public class ManualReportDetailsWindow extends AbstractWindow {
    private ManualTaskReport report;

    private Button download = new Button("Скачать отчет");
    private TextField grade = new TextField();
    private TextArea note = new TextArea("Заметка");
    private Button save = new Button("Сохранить оценку");

    private HomeworkManager homeworkManager;

    @Autowired
    public void setHomeworkManager(HomeworkManager homeworkManager) {
        this.homeworkManager = homeworkManager;
    }

    public void setReport(ManualTaskReport report) {
        this.report = report;
    }

    @Override
    public void init() {
        super.init();

        extendDownloadButton(new File(report.getFilePath()), download);
        save.addClickListener(e -> {
            if (isValid(grade)) {
                homeworkManager.rate(report, Registrants.getCurrent(), Integer.valueOf(grade.getValue()));
                close();
            } else {
                Notification.show("Неправильно заполнено поле");
            }
        });
    }


    @Override
    protected void fillOutLayout(VerticalLayout contentLayout) {
        contentLayout.removeAllComponents();
        if (report != null) {
            contentLayout.addComponent(createSingleFormLayout(new Label("Оценка (0..100)"), grade));
            contentLayout.addComponent(note);
            contentLayout.addComponent(createSingleFormLayout(download, save));
        }
    }
}
