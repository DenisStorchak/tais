package ua.org.tees.yarosh.tais.ui.core.components.windows;

import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;
import ua.org.tees.yarosh.tais.ui.configuration.SpringContextHelper;

import static com.vaadin.event.ShortcutAction.KeyCode.ENTER;
import static com.vaadin.event.ShortcutAction.KeyCode.ESCAPE;
import static ua.org.tees.yarosh.tais.ui.views.admin.CreateScheduleTaisView.CreateSchedulePresenter;

/**
 * @author Timur Yarosh
 *         Date: 21.03.14
 *         Time: 20:40
 */
public class CreateLessonWindow extends Window {

    private final CreateSchedulePresenter createSchedulePresenter;
    private CreateLessonWindow window;

    public CreateLessonWindow(CreateSchedulePresenter createSchedulePresenter) {
        super("Новая пара");
        window = this;
        setModal(true);
        setClosable(true);
        setResizable(false);
        addStyleName("edit-dashboard");
        setContent(new CreateTaskWindowContent());
        this.createSchedulePresenter = createSchedulePresenter;
    }

    public class CreateTaskWindowContent extends VerticalLayout {

        private ComboBox disciplines = new ComboBox("Дисциплина");
        private ComboBox registrants = new ComboBox("Преподаватель");
        private ComboBox classrooms = new ComboBox("Аудитория");
        private ComboBox lessonTypes = new ComboBox("Тип занятия");

        public CreateTaskWindowContent() {
            addComponent(new HorizontalLayout() {
                {
                    setMargin(true);
                    setSpacing(true);
                    addStyleName("footer");
                    setWidth("100%");
                    setCloseShortcut(ESCAPE);

                    Button ok = new Button("Создать");
                    ok.addClickListener(event -> {
                        Notification.show("Not implemented yet");
                        window.close();
                    });

                    SpringContextHelper ctx = new SpringContextHelper(VaadinServlet.getCurrent().getServletContext());


                    ok.addStyleName("wide");
                    ok.addStyleName("default");
                    ok.setClickShortcut(ENTER);
                    setComponentAlignment(ok, Alignment.BOTTOM_RIGHT);
                }
            });
        }
    }
}
