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
import ua.org.tees.yarosh.tais.auth.annotations.PermitRoles;
import ua.org.tees.yarosh.tais.ui.components.BgPanel;
import ua.org.tees.yarosh.tais.ui.components.DashPanel;
import ua.org.tees.yarosh.tais.ui.components.PlainBorderlessTable;
import ua.org.tees.yarosh.tais.ui.components.VerticalDash;
import ua.org.tees.yarosh.tais.ui.components.windows.CreateLessonWindow;
import ua.org.tees.yarosh.tais.ui.core.VaadinUtils;
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
    private Button editMondayButton = createEditScheduleButton();
    private Table mondayContent = new PlainBorderlessTable("Понедельник");
    private DashPanel mondaySchedule = createPanel(mondayContent, editMondayButton);

    private Button editTuesdayButton = createEditScheduleButton();
    private Table tuesdayContent = new PlainBorderlessTable("Вторник");
    private DashPanel tuesdaySchedule = createPanel(tuesdayContent, editTuesdayButton);

    private Button editWednesdayButton = createEditScheduleButton();
    private Table wednesdayContent = new PlainBorderlessTable("Среда");
    private DashPanel wednesdaySchedule = createPanel(wednesdayContent, editWednesdayButton);

    private Button editThursdayButton = createEditScheduleButton();
    private Table thursdayContent = new PlainBorderlessTable("Четверг");
    private DashPanel thursdaySchedule = createPanel(thursdayContent, editThursdayButton);

    private Button editFridayButton = createEditScheduleButton();
    private Table fridayContent = new PlainBorderlessTable("Пятница");
    private DashPanel fridaySchedule = createPanel(fridayContent, editFridayButton);

    private Button editSaturdayButton = createEditScheduleButton();
    private Table saturdayContent = new PlainBorderlessTable("Суббота");
    private DashPanel saturdaySchedule = createPanel(saturdayContent, editSaturdayButton);

    @Override
    public void setRegistrants(List<String> registrants) {
        this.registrants = registrants;
    }

    @Override
    public void setGroups(List<String> groups) {
        this.groups = groups;
    }

    public ScheduleView() {
        configureEditScheduleButton(editMondayButton, mondayContent);
        configureEditScheduleButton(editTuesdayButton, tuesdayContent);
        configureEditScheduleButton(editWednesdayButton, wednesdayContent);
        configureEditScheduleButton(editThursdayButton, thursdayContent);
        configureEditScheduleButton(editFridayButton, fridayContent);
        configureEditScheduleButton(editSaturdayButton, saturdayContent);

        periodFrom.setValue(new Date());
        periodTo.setValue(new Date());

        scheduleOwners.addValidator(new NotBlankValidator("Поле не должно быть пустым"));
        VaadinUtils.setValidationVisible(false, scheduleOwners);
        scheduleOwners.focus();

        VaadinUtils.setSizeUndefined(scheduleOwners, periodFrom, periodTo, searchLessonsButton);

        VaadinUtils.setSizeFull(this);
        addStyleName("dashboard-view");
        HorizontalLayout top = new BgPanel("Расписание занятий");
        addComponent(top);

        VerticalLayout dash = new VerticalDash();
        addComponent(dash);
        setExpandRatio(dash, 1.5f);

        dash.addComponent(createControlsLayout());
        dash.addComponents(
                createStage(mondaySchedule, tuesdaySchedule),
                createStage(wednesdaySchedule, thursdaySchedule),
                createStage(fridaySchedule, saturdaySchedule));
    }

    private void configureEditScheduleButton(Button editScheduleButton, Table scheduleContent) {
        editScheduleButton.addClickListener(event -> {
            CreateLessonWindow lessonWindow = new CreateLessonWindow(scheduleContent.getContainerDataSource());
            getUI().addWindow(lessonWindow);
            List<Lesson> editedLessons = lessonWindow.getLessons();
            if (editedLessons != null) {
                scheduleContent.setContainerDataSource(createDataSource(editedLessons));
            }
        });
    }

    private HorizontalLayout createControlsLayout() {
        HorizontalLayout controls = new HorizontalLayout();
        controls.setSizeUndefined();
        controls.addComponents(scheduleOwners, periodFrom, periodTo, searchLessonsButton);
        controls.setSpacing(true);
        return controls;
    }

    private HorizontalLayout createStage(Component... components) {
        HorizontalLayout stage = new HorizontalLayout(components);
        stage.setWidth(100, Unit.PERCENTAGE);
        stage.setSpacing(true);
        return stage;
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

    private DashPanel createPanel(Component content, Button configure) {
        DashPanel schedulePanel = new DashPanel();
        schedulePanel.addComponent(configure);
        schedulePanel.addComponent(content);
        content.setWidth(100, Unit.PERCENTAGE);
        return schedulePanel;
    }

    private Button createEditScheduleButton() {
        Button configure = new Button();
        configure.addStyleName("configure");
        configure.addStyleName("icon-doc-new");
        configure.addStyleName("icon-only");
        configure.addStyleName("borderless");
        configure.setDescription(EDIT_SCHEDULE_TITLE);
        configure.addStyleName("small");
        return configure;
    }
}
