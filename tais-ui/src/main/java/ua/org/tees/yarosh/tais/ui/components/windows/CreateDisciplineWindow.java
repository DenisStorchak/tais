package ua.org.tees.yarosh.tais.ui.components.windows;

import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import ua.org.tees.yarosh.tais.core.common.models.Discipline;
import ua.org.tees.yarosh.tais.core.common.models.Registrant;
import ua.org.tees.yarosh.tais.core.user.mgmt.api.service.RegistrantService;
import ua.org.tees.yarosh.tais.schedule.api.DisciplineService;
import ua.org.tees.yarosh.tais.ui.core.VaadinUtils;
import ua.org.tees.yarosh.tais.ui.core.api.AbstractWindow;
import ua.org.tees.yarosh.tais.ui.core.api.TaisWindow;
import ua.org.tees.yarosh.tais.ui.core.validators.NotBlankValidator;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.collect.Sets.newHashSet;
import static com.vaadin.event.ShortcutAction.KeyCode.ENTER;
import static com.vaadin.ui.Button.ClickEvent;
import static com.vaadin.ui.Notification.show;
import static ua.org.tees.yarosh.tais.ui.core.VaadinUtils.transformToIconOnlyButton;
import static ua.org.tees.yarosh.tais.ui.core.VaadinUtils.validOrThrow;

/**
 * @author Timur Yarosh
 *         Date: 21.03.14
 *         Time: 20:40
 */
@TaisWindow("Новая дисциплина")
public class CreateDisciplineWindow extends AbstractWindow {

    private TextField discipline = new TextField();
    private VerticalLayout teachersLayout = new VerticalLayout();
    private List<HorizontalLayout> teacherLines = new ArrayList<>();
    private List<ComboBox> teacherBoxes = new ArrayList<>();
    private Button addTeacher = new Button();
    private Button save = new Button("Сохранить");

    private RegistrantService registrantService;
    private DisciplineService disciplineService;

    @Autowired
    public void setRegistrantService(RegistrantService registrantService) {
        this.registrantService = registrantService;
    }

    @Autowired
    public void setDisciplineService(DisciplineService disciplineService) {
        this.disciplineService = disciplineService;
    }

    @Override
    public void init() {
        super.init();

        transformToIconOnlyButton("Добавить преподавателя", "icon-doc-new", this::addTeacherLine, addTeacher);

        save.addClickListener(this::saveDiscipline);
        save.addStyleName("wide");
        save.addStyleName("default");
        save.setClickShortcut(ENTER);

        discipline.addValidator(new NotBlankValidator("Поле не может быть пустым"));
        discipline.setValidationVisible(false);
        discipline.focus();

        teacherLines.add(createTeacherLine());
        teachersLayout.addComponent(teacherLines.get(0));
    }

    private HorizontalLayout createTeacherLine() {
        ComboBox teachers = new ComboBox();
        registrantService.findAllTeachers().forEach(teachers::addItem);
        teacherBoxes.add(teachers);

        return new HorizontalLayout(teachers, addTeacher);
    }

    private void addTeacherLine(ClickEvent event) {
        teacherLines.forEach(l -> l.removeComponent(addTeacher));
        HorizontalLayout newTeacherLine = createTeacherLine();

        teachersLayout.addComponent(newTeacherLine);
        teacherLines.add(newTeacherLine);
    }

    @Override
    protected void fillOutLayout(VerticalLayout contentLayout) {
        contentLayout.addComponent(discipline);
        contentLayout.addComponent(teachersLayout);
        contentLayout.addComponent(save);
        contentLayout.setComponentAlignment(save, Alignment.BOTTOM_RIGHT);
    }

    private void saveDiscipline(ClickEvent event) {
        try {
            teacherBoxes.forEach(VaadinUtils::validOrThrow);
            validOrThrow(discipline);

            Discipline disciplineModel = new Discipline();
            disciplineModel.setName(discipline.getValue());
            disciplineModel.setTeachers(newHashSet());
            teacherBoxes.forEach(b -> disciplineModel.getTeachers().add((Registrant) b.getValue()));
            disciplineService.createDiscipline(disciplineModel);
            close();
        } catch (IllegalArgumentException e) {
            show("Неправильное значение");
        }
    }
}
