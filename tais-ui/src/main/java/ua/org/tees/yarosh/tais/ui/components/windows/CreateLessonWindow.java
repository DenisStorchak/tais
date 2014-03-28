package ua.org.tees.yarosh.tais.ui.components.windows;

import com.vaadin.server.VaadinServlet;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.*;
import ua.org.tees.yarosh.tais.attendance.schedule.LessonType;
import ua.org.tees.yarosh.tais.attendance.schedule.api.ClassroomService;
import ua.org.tees.yarosh.tais.attendance.schedule.api.DisciplineService;
import ua.org.tees.yarosh.tais.attendance.schedule.models.Classroom;
import ua.org.tees.yarosh.tais.attendance.schedule.models.Lesson;
import ua.org.tees.yarosh.tais.core.common.models.Discipline;
import ua.org.tees.yarosh.tais.core.common.models.Registrant;
import ua.org.tees.yarosh.tais.core.user.mgmt.api.service.RegistrantService;
import ua.org.tees.yarosh.tais.ui.configuration.SpringContextHelper;

import java.util.Date;
import java.util.List;

import static com.vaadin.event.ShortcutAction.KeyCode.ENTER;
import static com.vaadin.event.ShortcutAction.KeyCode.ESCAPE;

/**
 * @author Timur Yarosh
 *         Date: 21.03.14
 *         Time: 20:40
 */
public class CreateLessonWindow extends Window {

    private CreateLessonWindow window;
    private Lesson lesson;

    public Lesson getLesson() {
        return lesson;
    }

    public CreateLessonWindow() {
        super("Новая пара");
        window = this;
        setModal(true);
        setClosable(true);
        setResizable(false);
        addStyleName("edit-dashboard");
        setContent(new CreateTaskWindowContent());
    }

    public class CreateTaskWindowContent extends VerticalLayout {

        private ComboBox registrants = new ComboBox("Преподаватель");
        private ComboBox disciplines = new ComboBox("Дисциплина");
        private ComboBox classrooms = new ComboBox("Аудитория");
        private ComboBox lessonTypes = new ComboBox("Тип занятия");
        private DateField startLessonDateTime = new DateField("Дата и время начала");

        public CreateTaskWindowContent() {
            addComponent(new HorizontalLayout() {
                {
                    startLessonDateTime.setResolution(Resolution.MINUTE);

                    SpringContextHelper ctx = new SpringContextHelper(VaadinServlet.getCurrent().getServletContext());
                    RegistrantService registrantService = ctx.getBean(RegistrantService.class);
                    DisciplineService disciplineService = ctx.getBean(DisciplineService.class);
                    ClassroomService classroomService = ctx.getBean(ClassroomService.class);

                    List<Registrant> allRegistrants = registrantService.findAllRegistrants();
                    allRegistrants.forEach(registrants::addItem);
                    List<Discipline> allDisciplines = disciplineService.findAllDisciplines();
                    allDisciplines.forEach(disciplines::addItem);
                    List<Classroom> allClassrooms = classroomService.findAllClassrooms();
                    allClassrooms.forEach(classrooms::addItem);
                    for (LessonType lessonType : LessonType.values()) {
                        lessonTypes.addItem(lessonType);
                    }

                    setMargin(true);
                    setSpacing(true);
                    addStyleName("footer");
                    setWidth("100%");
                    setCloseShortcut(ESCAPE);

                    Button ok = new Button("Создать");
                    ok.addClickListener(event -> {
                        String teacher = (String) registrants.getValue();
                        String discipline = (String) disciplines.getValue();
                        String classroom = (String) classrooms.getValue();
                        String lessonType = (String) lessonTypes.getValue();
                        Date startLesson = startLessonDateTime.getValue();

                        Lesson newLesson = new Lesson();
                        newLesson.setTeacher(registrantService.getRegistration(teacher));
                        newLesson.setDiscipline(disciplineService.findDisciplineByName(discipline));
                        newLesson.setClassroom(classroomService.findClassroom(classroom));
                        newLesson.setLessonType(LessonType.valueOf(lessonType));
                        newLesson.setDate(startLesson);
                        lesson = newLesson;
                        window.close();
                    });

                    ok.addStyleName("wide");
                    ok.addStyleName("default");
                    ok.setClickShortcut(ENTER);
                    VerticalLayout inputs = new VerticalLayout();
                    inputs.addComponents(registrants, disciplines, classrooms, lessonTypes, startLessonDateTime, ok);
                    inputs.setComponentAlignment(ok, Alignment.BOTTOM_RIGHT);
                    inputs.setSpacing(true);
                    addComponents(inputs);
                }
            });
        }
    }
}
