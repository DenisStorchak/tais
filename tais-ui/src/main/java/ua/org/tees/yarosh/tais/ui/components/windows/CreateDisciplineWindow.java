package ua.org.tees.yarosh.tais.ui.components.windows;

import com.vaadin.server.VaadinSession;
import com.vaadin.ui.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import ua.org.tees.yarosh.tais.ui.core.api.ComponentFactory;
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
public class CreateDisciplineWindow extends Window {

    private CreateDisciplineWindow window;

    public CreateDisciplineWindow() {
        super("Новая дисциплина");
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

                    TextField disciplineId = new TextField("Название дисциплины");
                    disciplineId.addValidator(new NotBlankValidator("Поле не может быть пустым"));
                    disciplineId.setValidationVisible(false);
                    addComponent(disciplineId);
                    setComponentAlignment(disciplineId, Alignment.TOP_LEFT);
                    setExpandRatio(disciplineId, 1);
                    disciplineId.focus();

                    Button ok = new Button("Создать");
                    addComponent(ok);
                    setComponentAlignment(ok, Alignment.BOTTOM_RIGHT);
                    ok.addClickListener(clickEvent -> {
                        if (disciplineId.isValid()) {
                            ((ComponentFactory) VaadinSession.getCurrent()
                                    .getAttribute(COMPONENT_FACTORY))
                                    .getPresenter(UserRegistrationPresenter.class)
                                    .createDiscipline(disciplineId.getValue());
                            window.close();
                        } else {
                            Notification.show("Неправильное значение");
                            disciplineId.focus();
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
