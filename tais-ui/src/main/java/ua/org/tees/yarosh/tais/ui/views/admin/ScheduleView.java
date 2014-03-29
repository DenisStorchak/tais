package ua.org.tees.yarosh.tais.ui.views.admin;

import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.PopupDateField;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import ua.org.tees.yarosh.tais.attendance.schedule.models.Lesson;
import ua.org.tees.yarosh.tais.auth.annotations.PermitRoles;
import ua.org.tees.yarosh.tais.ui.components.BgPanel;
import ua.org.tees.yarosh.tais.ui.components.Dash;
import ua.org.tees.yarosh.tais.ui.components.DashPanel;
import ua.org.tees.yarosh.tais.ui.core.mvp.PresentedBy;
import ua.org.tees.yarosh.tais.ui.core.mvp.PresenterBasedVerticalLayoutView;
import ua.org.tees.yarosh.tais.ui.core.validators.NotBlankValidator;
import ua.org.tees.yarosh.tais.ui.views.admin.api.ScheduleTaisView;
import ua.org.tees.yarosh.tais.ui.views.admin.presenters.ScheduleListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static ua.org.tees.yarosh.tais.core.common.dto.Role.ADMIN;
import static ua.org.tees.yarosh.tais.ui.core.text.UriFragments.Admin.MANAGED_SCHEDULE;

@PresentedBy(ScheduleListener.class)
@Service
@Qualifier(MANAGED_SCHEDULE)
@Scope("prototype")
@PermitRoles(ADMIN)
@SuppressWarnings("unchecked")
public class ScheduleView extends PresenterBasedVerticalLayoutView<ScheduleTaisView.SchedulePresenter>
        implements ScheduleTaisView {

    private static final String KEY_DISCIPLINE = "Дисциплина";
    private static final String KEY_LESSON_TYPE = "Тип занятия";
    private static final String KEY_TEACHER = "Преподаватель";
    private static final String KEY_CLASSROOM = "Аудитория";
    private static final String KEY_START_TIME = "Начало";
    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy',' EEEEEE", Locale.forLanguageTag("ru"));
    private static final SimpleDateFormat onlyTimeSdf = new SimpleDateFormat("hh:mm");
    private static final String EDIT_SCHEDULE_TITLE = "Редактировать расписание";
    private List<String> registrants;

    private List<String> groups;
    private ComboBox scheduleOwners = new ComboBox();
    private PopupDateField periodFrom = new PopupDateField();
    private PopupDateField periodTo = new PopupDateField();
    private Button searchLessonsButton = new Button("Поиск");
    private Button addScheduleButton = new Button("Добавить расписание");

    private Button editMondayButton = new Button(EDIT_SCHEDULE_TITLE);
    private DashPanel mondaySchedule = new DashPanel(editMondayButton);

    private Button editTuesdayButton = new Button(EDIT_SCHEDULE_TITLE);
    private DashPanel tuesdaySchedule = new DashPanel(editTuesdayButton);

    private Button editWednesdayButton = new Button(EDIT_SCHEDULE_TITLE);
    private DashPanel wednesdaySchedule = new DashPanel(editWednesdayButton);

    private Button editThursdayButton = new Button(EDIT_SCHEDULE_TITLE);
    private DashPanel thursdaySchedule = new DashPanel(editThursdayButton);

    private Button editFridayButton = new Button(EDIT_SCHEDULE_TITLE);
    private DashPanel fridaySchedule = new DashPanel(editFridayButton);

    private Button editSaturdayButton = new Button(EDIT_SCHEDULE_TITLE);
    private DashPanel saturdaySchedule = new DashPanel(editSaturdayButton);

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
        presenter().updateData();
        groups.forEach(scheduleOwners::addItem);
        registrants.forEach(scheduleOwners::addItem);
    }
}
