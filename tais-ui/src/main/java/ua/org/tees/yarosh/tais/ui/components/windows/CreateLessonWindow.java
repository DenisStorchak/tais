package ua.org.tees.yarosh.tais.ui.components.windows;

import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.server.VaadinServlet;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.*;
import org.apache.commons.lang3.RandomStringUtils;
import ua.org.tees.yarosh.tais.attendance.schedule.LessonType;
import ua.org.tees.yarosh.tais.attendance.schedule.api.ClassroomService;
import ua.org.tees.yarosh.tais.attendance.schedule.api.DisciplineService;
import ua.org.tees.yarosh.tais.attendance.schedule.models.Classroom;
import ua.org.tees.yarosh.tais.attendance.schedule.models.Lesson;
import ua.org.tees.yarosh.tais.core.common.models.Discipline;
import ua.org.tees.yarosh.tais.core.common.models.Registrant;
import ua.org.tees.yarosh.tais.core.user.mgmt.api.service.RegistrantService;
import ua.org.tees.yarosh.tais.ui.LessonTypeTranslator;
import ua.org.tees.yarosh.tais.ui.components.PlainBorderlessTable;
import ua.org.tees.yarosh.tais.ui.configuration.SpringContextHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.vaadin.event.ShortcutAction.KeyCode.ESCAPE;

/**
 * @author Timur Yarosh
 *         Date: 21.03.14
 *         Time: 20:40
 */
@SuppressWarnings("unchecked")
public class CreateLessonWindow extends Window {

    private static final String DISCIPLINE = "Дисциплина";
    private static final String LESSON_TYPE = "Тип занятия";
    private static final String CLASSROOM = "Аудитория";
    private static final String TEACHER = "Преподаватель";
    private static final String TIME = "Начало";
    private CreateLessonWindow window;
    private List<Lesson> lessons = new ArrayList<>();

    private Container scheduleContainer;
    private PlainBorderlessTable schedule;

    private SpringContextHelper ctx = new SpringContextHelper(VaadinServlet.getCurrent().getServletContext());
    private DisciplineService disciplineService = ctx.getBean(DisciplineService.class);
    private ClassroomService classroomService = ctx.getBean(ClassroomService.class);
    private RegistrantService registrantService = ctx.getBean(RegistrantService.class);


    public List<Lesson> getLessons() {
        return lessons;
    }

    public CreateLessonWindow(Container scheduleContainer) {
        super("Редактировать расписание");
        window = this;
        this.scheduleContainer = scheduleContainer;
        setModal(true);
        setClosable(true);
        setResizable(false);
        addStyleName("edit-dashboard");
        setContent(new CreateTaskWindowContent());
        setSizeUndefined();
    }

    public class CreateTaskWindowContent extends VerticalLayout {

        private Button saveChangesButton = new Button("Сохранить изменения");
        private Button addLessonButton = new Button("Добавить запись");

        public CreateTaskWindowContent() {

            setMargin(true);
            setSpacing(true);
            addStyleName("footer");
            setCloseShortcut(ESCAPE);
            setSizeUndefined();

            schedule = new PlainBorderlessTable("") {
                {
                    setEditable(true);
                    setContainerDataSource(convertEditable(scheduleContainer));
                }
            };
            schedule.setSizeUndefined();
            addComponent(schedule);

            saveChangesButton.addClickListener(event -> {
                Container dataSource = schedule.getContainerDataSource();
                for (Object id : dataSource.getItemIds()) {
                    Item item = dataSource.getItem(id);
                    ComboBox disciplines = (ComboBox) item.getItemProperty(DISCIPLINE).getValue();
                    Discipline discipline = (Discipline) disciplines.getValue();

                    ComboBox lessonTypes = ((ComboBox) item.getItemProperty(LESSON_TYPE).getValue());
                    String lessonType = (String) lessonTypes.getValue();

                    ComboBox classrooms = (ComboBox) item.getItemProperty(CLASSROOM);
                    Classroom classroom = (Classroom) classrooms.getValue();

                    ComboBox teachers = (ComboBox) item.getItemProperty(TEACHER);
                    Registrant teacher = (Registrant) teachers.getValue();

                    DateField lessonDate = (DateField) item.getItemProperty(TIME);
                    Date date = lessonDate.getValue();

                    Lesson lesson = new Lesson();
                    lesson.setDiscipline(discipline);
                    lesson.setLessonType(LessonType.valueOf(LessonTypeTranslator.translate(lessonType).toUpperCase()));
                    lesson.setClassroom(classroom);
                    lesson.setTeacher(teacher);
                    lesson.setDate(date);
                    lessons.add(lesson);
                }
                window.close();
            });
            saveChangesButton.addStyleName("default");
            addLessonButton.addClickListener(event -> {
                Item item = schedule.addItem(RandomStringUtils.random(5));
                item.getItemProperty(DISCIPLINE).setValue(createDisciplines(disciplineService));
                item.getItemProperty(LESSON_TYPE).setValue(createLessonTypes());
                item.getItemProperty(CLASSROOM).setValue(createClassrooms(classroomService));
                item.getItemProperty(TEACHER).setValue(createTeachers(registrantService));
                item.getItemProperty(TIME).setValue(createDateField());
            });
            HorizontalLayout controls = new HorizontalLayout(addLessonButton, saveChangesButton);
            controls.setSpacing(true);
            addComponent(controls);
            setComponentAlignment(controls, Alignment.BOTTOM_RIGHT);
        }

        private Container convertEditable(Container scheduleContainer) {
            Container editable = new IndexedContainer();
            editable.addContainerProperty(DISCIPLINE, ComboBox.class, null);
            editable.addContainerProperty(LESSON_TYPE, ComboBox.class, null);
            editable.addContainerProperty(CLASSROOM, ComboBox.class, null);
            editable.addContainerProperty(TEACHER, ComboBox.class, null);
            editable.addContainerProperty(TIME, DateField.class, null);

            for (Integer i = 0; i < scheduleContainer.getItemIds().size(); i++) {
                Item sourceItem = scheduleContainer.getItem(scheduleContainer.getItemIds().toArray()[i]);
                Item editableItem = editable.addItem(i);
                convertEditable(disciplineService, classroomService, registrantService, sourceItem, editableItem);
            }
            return editable;
        }

        private void convertEditable(DisciplineService disciplineService,
                                     ClassroomService classroomService,
                                     RegistrantService registrantService,
                                     Item sourceItem,
                                     Item editableItem) {

            String discipline = (String) sourceItem.getItemProperty(DISCIPLINE).getValue();
            ComboBox disciplines = createDisciplines(disciplineService);
            disciplines.setValue(discipline);
            editableItem.getItemProperty(DISCIPLINE).setValue(disciplines);

            String lessonType = (String) sourceItem.getItemProperty(LESSON_TYPE).getValue();
            ComboBox lessonTypes = createLessonTypes();
            lessonTypes.setValue(lessonType);
            editableItem.getItemProperty(LESSON_TYPE).setValue(lessonTypes);

            String classroom = (String) sourceItem.getItemProperty(CLASSROOM).getValue();
            ComboBox classrooms = createClassrooms(classroomService);
            classrooms.setValue(classroom);
            editableItem.getItemProperty(CLASSROOM).setValue(classrooms);

            String teacher = (String) sourceItem.getItemProperty(TEACHER).getValue();
            ComboBox teachers = createTeachers(registrantService);
            teachers.setValue(teacher);
            editableItem.getItemProperty(TEACHER).setValue(teachers);

            Date date = (Date) sourceItem.getItemProperty(TIME).getValue();
            DateField dateField = createDateField();
            dateField.setResolution(Resolution.MINUTE);
            dateField.setValue(date);
            editableItem.getItemProperty(TIME).setValue(dateField);
        }
    }

    private DateField createDateField() {
        DateField dateField = new DateField();
        dateField.setResolution(Resolution.MINUTE);
        return dateField;
    }

    private ComboBox createDisciplines(DisciplineService disciplineService) {
        ComboBox disciplines = new ComboBox();
        disciplineService.findAllDisciplines().forEach(disciplines::addItem);
        return disciplines;
    }

    private ComboBox createTeachers(RegistrantService registrantService) {
        ComboBox teachers = new ComboBox();
        registrantService.findAllRegistrants().forEach(teachers::addItem);
        return teachers;
    }

    private ComboBox createClassrooms(ClassroomService classroomService) {
        ComboBox classrooms = new ComboBox();
        classroomService.findAllClassrooms().forEach(classrooms::addItem);
        return classrooms;
    }

    private ComboBox createLessonTypes() {
        ComboBox lessonTypes = new ComboBox();
        for (LessonType type : LessonType.values()) {
            lessonTypes.addItem(LessonTypeTranslator.translate(type.toString()));
        }
        lessonTypes.setWidth(100, Unit.PERCENTAGE);
        return lessonTypes;
    }
}
