package ua.org.tees.yarosh.tais.ui.core.components;

import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import ua.org.tees.yarosh.tais.homework.models.ManualTaskReport;

/**
 * @author Timur Yarosh
 *         Date: 22.03.14
 *         Time: 12:02
 */
public class UnratedReportsDataSource extends IndexedContainer {

    private static final String DISCIPLINE_KEY = "Дисциплина";
    private static final String STUDENT_GROUP_KEY = "Группа";
    private static final String STUDENT_KEY = "Студент";
    private static final String INTERACTION_KEY = "Взаимодействие";

    public UnratedReportsDataSource() {
        addContainerProperty(DISCIPLINE_KEY, String.class, "Unknown discipline");
        addContainerProperty(STUDENT_GROUP_KEY, String.class, "Unknown student group");
        addContainerProperty(STUDENT_KEY, String.class, "Inknowk owner");
        addContainerProperty(INTERACTION_KEY, Button.class, null);
    }

    public void addReport(ManualTaskReport taskReport) {
        String discipline = taskReport.getTask().getDiscipline().getName();
        String studentGroup = taskReport.getOwner().getGroup().getId();
        String student = taskReport.getOwner().getName() + " " + taskReport.getOwner().getSurname();
        Button open = new Button("Подробнее", clickEvent -> Notification.show("Not implemented yet"));
        open.addStyleName("icon-article-alt");
//        open.addStyleName("icon-only");

        Item item = addItem(taskReport.getId());
        item.getItemProperty(DISCIPLINE_KEY).setValue(discipline);
        item.getItemProperty(STUDENT_GROUP_KEY).setValue(studentGroup);
        item.getItemProperty(STUDENT_KEY).setValue(student);
        item.getItemProperty(INTERACTION_KEY).setValue(open);
    }
}
