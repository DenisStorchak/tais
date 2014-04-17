package ua.org.tees.yarosh.tais.ui.views.admin;

import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import ua.org.tees.yarosh.tais.auth.annotations.PermitRoles;
import ua.org.tees.yarosh.tais.core.common.models.Discipline;
import ua.org.tees.yarosh.tais.core.common.models.Registrant;
import ua.org.tees.yarosh.tais.schedule.models.Classroom;
import ua.org.tees.yarosh.tais.schedule.models.Lesson;
import ua.org.tees.yarosh.tais.ui.LessonTypeTranslator;
import ua.org.tees.yarosh.tais.ui.components.PlainBorderlessTable;
import ua.org.tees.yarosh.tais.ui.components.layouts.DashPanel;
import ua.org.tees.yarosh.tais.ui.components.layouts.DashboardView;
import ua.org.tees.yarosh.tais.ui.core.UIFactoryAccessor;
import ua.org.tees.yarosh.tais.ui.core.VaadinUtils;
import ua.org.tees.yarosh.tais.ui.core.mvp.PresentedBy;
import ua.org.tees.yarosh.tais.ui.core.mvp.TaisView;
import ua.org.tees.yarosh.tais.ui.core.validators.NotBlankValidator;
import ua.org.tees.yarosh.tais.ui.views.admin.api.ScheduleTaisView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static ua.org.tees.yarosh.tais.core.common.dto.Roles.ADMIN;
import static ua.org.tees.yarosh.tais.ui.core.DataBinds.UriFragments.Admin.MANAGED_SCHEDULE;
import static ua.org.tees.yarosh.tais.ui.core.VaadinUtils.isValid;
import static ua.org.tees.yarosh.tais.ui.core.VaadinUtils.setValidationVisible;
import static ua.org.tees.yarosh.tais.ui.views.admin.api.ScheduleTaisView.SchedulePresenter;

@PresentedBy(SchedulePresenter.class)
@TaisView
@Qualifier(MANAGED_SCHEDULE)
@PermitRoles(ADMIN)
@SuppressWarnings("unchecked")
public class ScheduleView extends DashboardView implements ScheduleTaisView {

    private static final String KEY_DISCIPLINE = "Дисциплина";
    private static final String KEY_LESSON_TYPE = "Тип занятия";
    private static final String KEY_TEACHER = "Преподаватель";
    private static final String KEY_CLASSROOM = "Аудитория";
    private static final String KEY_START_TIME = "Начало";
    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy',' EEEEEE", Locale.forLanguageTag("ru"));
    private static final SimpleDateFormat onlyTimeSdf = new SimpleDateFormat("hh:mm");
    private static final String EDIT_SCHEDULE_TITLE = "Редактировать расписание";
    private final Table scheduleTable;
    private SortedMap<String, List<Lesson>> lessons = new TreeMap<>((o1, o2) -> {
        try {
            Date d1 = sdf.parse(o1);
            Date d2 = sdf.parse(o2);
            if (d1.before(d2)) return -1;
            if (d1.equals(d2)) return 0;
            return 1;
        } catch (ParseException e) {
            throw new IllegalArgumentException(e);
        }
    });

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
        SchedulePresenter presenter = UIFactoryAccessor.getCurrent().getRelativePresenter(this, SchedulePresenter.class);
        scheduleOwners.removeAllItems();
        presenter.getGroups().forEach(scheduleOwners::addItem);
        presenter.getRegistrants().forEach(scheduleOwners::addItem);
    }

    private void updateLessons() {
        Map<? extends Date, ? extends List<Lesson>> schedule = UIFactoryAccessor.getCurrent()
                .getRelativePresenter(this, SchedulePresenter.class)
                .getSchedule(scheduleOwners.getValue(), periodFrom.getValue(), periodTo.getValue());
        lessons.clear();
        for (Date date : schedule.keySet()) {
            lessons.put(sdf.format(date), schedule.get(date));
        }
    }

    public ScheduleView() {
        super();
        periodFrom.setValue(new Date());
        periodTo.setValue(new Date());

        scheduleOwners.addValidator(new NotBlankValidator("Поле не должно быть пустым"));
        setValidationVisible(false, scheduleOwners);
        scheduleOwners.focus();

        VaadinUtils.setSizeUndefined(scheduleOwners, periodFrom, periodTo, searchLessonsButton);

        addScheduleButton.addClickListener(event -> getUI().addWindow(
                UIFactoryAccessor.getCurrent()
                        .getRelativePresenter(this, SchedulePresenter.class)
                        .getCreateScheduleWindow(scheduleOwners.getValue(), null, periodTo.getValue())
        ));

        scheduleTable = new PlainBorderlessTable("Расписание");
        scheduleTable.addContainerProperty("", DashPanel.class, null);
        scheduleTable.setSizeFull();
        searchLessonsButton.addClickListener(event -> {
            scheduleTable.removeAllItems();
            updateLessons();
            for (String date : lessons.keySet()) {
                Button editScheduleButton = createEditScheduleButton();
                Table dayContent = new Table(date);
                dayContent.setContainerDataSource(createDataSource(lessons.get(date)));
                configureEditScheduleButton(editScheduleButton, dayContent);
                DashPanel dayPanel = createPanel(dayContent, editScheduleButton);
                Item item = scheduleTable.addItem(RandomStringUtils.random(10));
                item.getItemProperty("").setValue(dayPanel);
            }
        });

        VaadinUtils.setSizeFull(this);
        top.addComponent(createControlsLayout());

        addDashPanel(null, null, scheduleTable);
    }

    private void configureEditScheduleButton(Button editScheduleButton, Table scheduleContent) {
        editScheduleButton.addClickListener(event -> getUI().addWindow(
                UIFactoryAccessor.getCurrent()
                        .getRelativePresenter(this, SchedulePresenter.class)
                        .getCreateScheduleWindow(scheduleOwners.getValue(), scheduleContent, periodTo.getValue())
        ));
    }

    private HorizontalLayout createControlsLayout() {
        HorizontalLayout controls = new HorizontalLayout();
        controls.setSizeUndefined();
        controls.addComponents(scheduleOwners, periodFrom, periodTo, searchLessonsButton, addScheduleButton);
        controls.setSpacing(true);
        return controls;
    }

    private Container createDataSource(List<Lesson> lessons) {
        IndexedContainer container = new IndexedContainer();
        container.addContainerProperty(KEY_DISCIPLINE, Discipline.class, null);
        container.addContainerProperty(KEY_LESSON_TYPE, String.class, null);
        container.addContainerProperty(KEY_TEACHER, Registrant.class, null);
        container.addContainerProperty(KEY_CLASSROOM, Classroom.class, null);
        container.addContainerProperty(KEY_START_TIME, Date.class, null);

        for (Lesson lesson : lessons) {
            Item item = container.addItem(lesson.getId());
            item.getItemProperty(KEY_DISCIPLINE).setValue(lesson.getDiscipline());
            item.getItemProperty(KEY_LESSON_TYPE).setValue(LessonTypeTranslator.translate(lesson.getLessonType().toString()));
            item.getItemProperty(KEY_TEACHER).setValue(lesson.getTeacher());
            item.getItemProperty(KEY_CLASSROOM).setValue(lesson.getClassroom());
            item.getItemProperty(KEY_START_TIME).setValue(lesson.getDate());
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
