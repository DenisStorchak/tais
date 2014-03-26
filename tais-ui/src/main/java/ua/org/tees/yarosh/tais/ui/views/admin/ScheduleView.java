package ua.org.tees.yarosh.tais.ui.views.admin;

import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import ua.org.tees.yarosh.tais.attendance.schedule.models.Lesson;
import ua.org.tees.yarosh.tais.ui.components.BgPanel;
import ua.org.tees.yarosh.tais.ui.components.Dash;
import ua.org.tees.yarosh.tais.ui.components.DashPanel;
import ua.org.tees.yarosh.tais.ui.components.PlainBorderlessTable;
import ua.org.tees.yarosh.tais.ui.core.mvp.PresenterBasedVerticalLayoutView;
import ua.org.tees.yarosh.tais.ui.core.mvp.ProducedBy;
import ua.org.tees.yarosh.tais.ui.core.validators.NotBlankValidator;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static ua.org.tees.yarosh.tais.ui.core.UriFragments.Admin.CREATE_SCHEDULE;
import static ua.org.tees.yarosh.tais.ui.core.UriFragments.Admin.MANAGED_SCHEDULE;
import static ua.org.tees.yarosh.tais.ui.views.admin.ScheduleTaisView.SchedulePresenter;

@ProducedBy(ScheduleListener.class)
@Service
@Qualifier(MANAGED_SCHEDULE)
@Scope("prototype")
@SuppressWarnings("unchecked")
public class ScheduleView extends PresenterBasedVerticalLayoutView<SchedulePresenter> implements ScheduleTaisView {

    private static final String KEY_DISCIPLINE = "Дисциплина";
    private static final String KEY_LESSON_TYPE = "Тип занятия";
    private static final String KEY_TEACHER = "Преподаватель";
    private static final String KEY_CLASSROOM = "Аудитория";
    private static final String KEY_START_TIME = "Начало";
    private List<String> registrants;
    private List<String> groups;

    private ComboBox scheduleOwners = new ComboBox();
    private PopupDateField periodFrom = new PopupDateField();
    private PopupDateField periodTo = new PopupDateField();
    private Button searchLessonsButton = new Button("Поиск");
    private Button addScheduleButton = new Button("Добавить расписание");

    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy',' EEEEEE", Locale.forLanguageTag("ru"));
    private static final SimpleDateFormat onlyTimeSdf = new SimpleDateFormat("hh:mm");

    @Override
    public void setRegistrants(List<String> registrants) {
        this.registrants = registrants;
    }

    @Override
    public void setGroups(List<String> groups) {
        this.groups = groups;
    }

    public ScheduleView() {
        periodFrom.setValue(new Date());
        periodTo.setValue(new Date());
        setSizeFull();
        addStyleName("dashboard-view");
        HorizontalLayout top = new BgPanel("Расписание занятий");
        addComponent(top);

        HorizontalLayout dash = new Dash();
        addComponent(dash);
        setExpandRatio(dash, 1.5f);

        scheduleOwners.addValidator(new NotBlankValidator("Поле не должно быть пустым"));
        scheduleOwners.setValidationVisible(false);
        scheduleOwners.focus();

        addScheduleButton.addClickListener(event -> getUI().getNavigator().navigateTo(CREATE_SCHEDULE));

        searchLessonsButton.addClickListener(event -> {
            if (scheduleOwners.isValid()) {
                Map<? extends Date, ? extends List<Lesson>> schedule = primaryPresenter().getSchedule(
                        (String) scheduleOwners.getValue(), periodFrom.getValue(), periodTo.getValue());
                if (schedule.isEmpty()) {
                    Notification.show("Расписание не найдено");
                }
                for (Date date : schedule.keySet()) {
                    DashPanel dayPanel = new DashPanel();
                    dash.addComponent(dayPanel);
                    PlainBorderlessTable scheduleTable = new PlainBorderlessTable(sdf.format(date));
                    dayPanel.addComponent(scheduleTable);
                    scheduleTable.setContainerDataSource(createDataSource(schedule.get(date)));
                }
            } else {
                Notification.show("Выберите пользователя расписания");
                scheduleOwners.focus();
            }
        });

        HorizontalLayout controls = new HorizontalLayout();
        controls.setSizeUndefined();
        controls.addComponents(scheduleOwners, periodFrom, periodTo, searchLessonsButton, addScheduleButton);
        controls.setExpandRatio(scheduleOwners, 1.5f);
        controls.setExpandRatio(periodFrom, 1.5f);
        controls.setExpandRatio(periodTo, 1.5f);
        controls.setExpandRatio(searchLessonsButton, 1.5f);
        controls.setExpandRatio(addScheduleButton, 1.5f);
        controls.setSpacing(true);

        scheduleOwners.setSizeUndefined();
        periodFrom.setSizeUndefined();
        periodTo.setSizeUndefined();
        searchLessonsButton.setSizeUndefined();

        dash.addComponent(controls);
        dash.setSpacing(true);
    }

    private Container createDataSource(List<Lesson> lessons) {
        IndexedContainer container = new IndexedContainer();
        container.addContainerProperty(KEY_DISCIPLINE, String.class, null);
        container.addContainerProperty(KEY_LESSON_TYPE, String.class, null);
        container.addContainerProperty(KEY_TEACHER, String.class, null);
        container.addContainerProperty(KEY_CLASSROOM, String.class, null);
        container.addContainerProperty(KEY_START_TIME, String.class, null);

        for (Lesson lesson : lessons) {
            Item item = container.addItem(lesson.getId());
            item.getItemProperty(KEY_DISCIPLINE).setValue(lesson.getDiscipline());
            item.getItemProperty(KEY_LESSON_TYPE).setValue(lesson.getLessonType().toString());
            item.getItemProperty(KEY_TEACHER).setValue(String.format("%s %s %s",
                    lesson.getTeacher().getSurname(),
                    lesson.getTeacher().getName(),
                    lesson.getTeacher().getPatronymic()));
            item.getItemProperty(KEY_CLASSROOM).setValue(lesson.getClassroom().getId());
            item.getItemProperty(KEY_START_TIME).setValue(onlyTimeSdf.format(lesson.getDate()));
        }
        return container;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        getPresenters().forEach(SchedulePresenter::updateData);
        groups.forEach(scheduleOwners::addItem);
        registrants.forEach(scheduleOwners::addItem);
    }
}
