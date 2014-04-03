package ua.org.tees.yarosh.tais.ui.views.admin;

import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import ua.org.tees.yarosh.tais.auth.annotations.PermitRoles;
import ua.org.tees.yarosh.tais.core.common.models.Discipline;
import ua.org.tees.yarosh.tais.core.common.models.Registrant;
import ua.org.tees.yarosh.tais.schedule.models.Classroom;
import ua.org.tees.yarosh.tais.schedule.models.Lesson;
import ua.org.tees.yarosh.tais.ui.components.BgPanel;
import ua.org.tees.yarosh.tais.ui.components.DashPanel;
import ua.org.tees.yarosh.tais.ui.components.PlainBorderlessTable;
import ua.org.tees.yarosh.tais.ui.components.VerticalDash;
import ua.org.tees.yarosh.tais.ui.core.SessionFactory;
import ua.org.tees.yarosh.tais.ui.core.VaadinUtils;
import ua.org.tees.yarosh.tais.ui.core.mvp.AbstractTaisLayout;
import ua.org.tees.yarosh.tais.ui.core.mvp.PresentedBy;
import ua.org.tees.yarosh.tais.ui.core.validators.NotBlankValidator;
import ua.org.tees.yarosh.tais.ui.views.admin.api.ScheduleTaisView;

import java.text.SimpleDateFormat;
import java.util.*;

import static com.vaadin.server.Sizeable.Unit.PERCENTAGE;
import static ua.org.tees.yarosh.tais.core.common.dto.Role.ADMIN;
import static ua.org.tees.yarosh.tais.ui.core.DataBinds.UriFragments.Admin.MANAGED_SCHEDULE;
import static ua.org.tees.yarosh.tais.ui.core.VaadinUtils.isValid;
import static ua.org.tees.yarosh.tais.ui.core.VaadinUtils.setValidationVisible;
import static ua.org.tees.yarosh.tais.ui.views.admin.api.ScheduleTaisView.SchedulePresenter;

@PresentedBy(SchedulePresenter.class)
@Service
@Qualifier(MANAGED_SCHEDULE)
@Scope("prototype")
@PermitRoles(ADMIN)
@SuppressWarnings("unchecked")
public class ScheduleView extends AbstractTaisLayout implements ScheduleTaisView {

    private static final String KEY_DISCIPLINE = "Дисциплина";
    private static final String KEY_LESSON_TYPE = "Тип занятия";
    private static final String KEY_TEACHER = "Преподаватель";
    private static final String KEY_CLASSROOM = "Аудитория";
    private static final String KEY_START_TIME = "Начало";
    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy',' EEEEEE", Locale.forLanguageTag("ru"));
    private static final SimpleDateFormat onlyTimeSdf = new SimpleDateFormat("hh:mm");
    private static final String EDIT_SCHEDULE_TITLE = "Редактировать расписание";
    private final VerticalLayout lessonsLayout;
    private Map<Date, List<Lesson>> lessons = new HashMap<>();

    private ComboBox scheduleOwners = new ComboBox();
    private PopupDateField periodFrom = new PopupDateField();
    private PopupDateField periodTo = new PopupDateField();
    private Button searchLessonsButton = new Button("Поиск");
    private Button addScheduleButton = new Button("Добавить расписание");

    @Override
    public void update() {
        if (isValid(scheduleOwners, periodFrom, periodTo)) {
            updateLessons();
        }
        SchedulePresenter presenter = SessionFactory.getCurrent().getRelativePresenter(this, SchedulePresenter.class);
        scheduleOwners.removeAllItems();
        presenter.getGroups().forEach(scheduleOwners::addItem);
        presenter.getRegistrants().forEach(scheduleOwners::addItem);
    }

    private void updateLessons() {
        Map<? extends Date, ? extends List<Lesson>> schedule = SessionFactory.getCurrent()
                .getRelativePresenter(this, SchedulePresenter.class)
                .getSchedule(scheduleOwners.getValue(), periodFrom.getValue(), periodTo.getValue());
        lessons.clear();
        for (Date date : schedule.keySet()) {
            lessons.put(date, schedule.get(date));
        }
    }

    public ScheduleView() {
        periodFrom.setValue(new Date());
        periodTo.setValue(new Date());

        scheduleOwners.addValidator(new NotBlankValidator("Поле не должно быть пустым"));
        setValidationVisible(false, scheduleOwners);
        scheduleOwners.focus();

        VaadinUtils.setSizeUndefined(scheduleOwners, periodFrom, periodTo, searchLessonsButton);

        addScheduleButton.addClickListener(event -> getUI().addWindow(
                SessionFactory.getCurrent()
                        .getRelativePresenter(this, SchedulePresenter.class)
                        .getCreateScheduleWindow(scheduleOwners.getValue(), null, periodTo.getValue())
        ));

        lessonsLayout = new VerticalLayout();
        lessonsLayout.setSizeFull();
        searchLessonsButton.addClickListener(event -> {
            lessonsLayout.removeAllComponents();
            updateLessons();
            for (Date date : lessons.keySet()) {
                Button editScheduleButton = createEditScheduleButton();
                Table dayContent = new PlainBorderlessTable(sdf.format(date));
                dayContent.setContainerDataSource(createDataSource(lessons.get(date)));
                configureEditScheduleButton(editScheduleButton, dayContent);
                DashPanel dayPanel = createPanel(dayContent, editScheduleButton);
                lessonsLayout.addComponent(dayPanel);
            }
        });

        VaadinUtils.setSizeFull(this);
        addStyleName("dashboard-view");
        HorizontalLayout top = new BgPanel("Расписание занятий");
        top.addComponent(createControlsLayout());
        addComponent(top);

        VerticalLayout dash = new VerticalDash();
        addComponent(dash);
        setExpandRatio(dash, 1.5f);

        dash.addComponent(lessonsLayout);
    }

    private void configureEditScheduleButton(Button editScheduleButton, Table scheduleContent) {
        editScheduleButton.addClickListener(event -> getUI().addWindow(
                SessionFactory.getCurrent()
                        .getRelativePresenter(this, SchedulePresenter.class)
                        .getCreateScheduleWindow(scheduleOwners.getValue(), scheduleContent, periodTo.getValue())
        ));
    }

    private HorizontalLayout createControlsLayout() {
        HorizontalLayout controls = new HorizontalLayout();
        controls.setHeight(20, PERCENTAGE);
        controls.addComponents(scheduleOwners, periodFrom, periodTo, searchLessonsButton, addScheduleButton);
        controls.setSpacing(true);
        return controls;
    }

    private HorizontalLayout createStage(Component... components) {
        HorizontalLayout stage = new HorizontalLayout(components);
        stage.setWidth(100, PERCENTAGE);
        stage.setSpacing(true);
        return stage;
    }

    private Container createDataSource(List<Lesson> lessons) {
        IndexedContainer container = new IndexedContainer();
        container.addContainerProperty(KEY_DISCIPLINE, Discipline.class, null);
        container.addContainerProperty(KEY_LESSON_TYPE, String.class, null);
        container.addContainerProperty(KEY_TEACHER, Registrant.class, null);
        container.addContainerProperty(KEY_CLASSROOM, Classroom.class, null);
        container.addContainerProperty(KEY_START_TIME, String.class, null);

        for (Lesson lesson : lessons) {
            Item item = container.addItem(lesson.getId());
            item.getItemProperty(KEY_DISCIPLINE).setValue(lesson.getDiscipline());
            item.getItemProperty(KEY_LESSON_TYPE).setValue(lesson.getLessonType().toString());
            item.getItemProperty(KEY_TEACHER).setValue(lesson.getTeacher());
            item.getItemProperty(KEY_CLASSROOM).setValue(lesson.getClassroom());
            item.getItemProperty(KEY_START_TIME).setValue(onlyTimeSdf.format(lesson.getDate()));
        }
        return container;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        update();
    }

    private DashPanel createPanel(Component content, Button configure) {
        DashPanel schedulePanel = new DashPanel();
        schedulePanel.addComponent(configure);
        schedulePanel.addComponent(content);
        content.setWidth(100, PERCENTAGE);
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
