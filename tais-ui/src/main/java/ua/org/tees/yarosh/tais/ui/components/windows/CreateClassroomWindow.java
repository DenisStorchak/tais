package ua.org.tees.yarosh.tais.ui.components.windows;

import com.vaadin.server.VaadinSession;
import com.vaadin.ui.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import ua.org.tees.yarosh.tais.ui.core.ComponentFactory;
import ua.org.tees.yarosh.tais.ui.core.validators.NotBlankValidator;

import static com.vaadin.event.ShortcutAction.KeyCode.ENTER;
import static com.vaadin.event.ShortcutAction.KeyCode.ESCAPE;
import static ua.org.tees.yarosh.tais.ui.core.DataBinds.SessionKeys.COMPONENT_FACTORY;
import static ua.org.tees.yarosh.tais.ui.views.admin.api.UserRegistrationTaisView.UserRegistrationPresenter;

/**
 * @author Timur Yarosh
 *         Date: 21.03.14
 *         Time: 20:40
 */
@Service
@Scope("prototype")
public class CreateClassroomWindow extends Window {

    private CreateClassroomWindow window;

    public CreateClassroomWindow() {
        super("Новая аудитория");
        window = this;
        setModal(true);
        setClosable(true);
        setResizable(false);
        addStyleName("edit-dashboard");
        setContent(new CreateTaskWindowContent());
    }

    public class CreateTaskWindowContent extends VerticalLayout {
        public CreateTaskWindowContent() {
            addComponent(new HorizontalLayout() {
                {
                    setMargin(true);
                    setSpacing(true);
                    addStyleName("footer");
                    setWidth("100%");
                    setCloseShortcut(ESCAPE);

                    TextField classroom = new TextField("№ аудитории");
                    classroom.addValidator(new NotBlankValidator("Поле не может быть пустым"));
                    classroom.setValidationVisible(false);
                    addComponent(classroom);
                    setComponentAlignment(classroom, Alignment.TOP_LEFT);
                    setExpandRatio(classroom, 1);
                    classroom.focus();

                    Button ok = new Button("Создать");
                    addComponent(ok);
                    setComponentAlignment(ok, Alignment.BOTTOM_RIGHT);
                    ok.addClickListener(clickEvent -> {
                        if (classroom.isValid()) {
                            ((ComponentFactory) VaadinSession.getCurrent()
                                    .getAttribute(COMPONENT_FACTORY))
                                    .getPresenter(UserRegistrationPresenter.class)
                                    .createClassroom(classroom.getValue());
                            window.close();
                        } else {
                            Notification.show("Неправильное значение");
                            classroom.focus();
                        }
                    });
                    ok.addStyleName("wide");
                    ok.addStyleName("default");
                    ok.setClickShortcut(ENTER);
                }
            });
        }
    }
}
