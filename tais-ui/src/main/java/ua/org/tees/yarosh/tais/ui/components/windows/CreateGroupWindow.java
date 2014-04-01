package ua.org.tees.yarosh.tais.ui.components.windows;

import com.vaadin.server.VaadinServlet;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.*;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import ua.org.tees.yarosh.tais.core.common.models.StudentGroup;
import ua.org.tees.yarosh.tais.core.user.mgmt.api.service.RegistrantService;
import ua.org.tees.yarosh.tais.ui.core.ComponentFactory;
import ua.org.tees.yarosh.tais.ui.core.text.SessionKeys;
import ua.org.tees.yarosh.tais.ui.core.validators.NotBlankValidator;

import java.util.ArrayList;

import static com.vaadin.event.ShortcutAction.KeyCode.ENTER;
import static com.vaadin.event.ShortcutAction.KeyCode.ESCAPE;
import static ua.org.tees.yarosh.tais.ui.views.admin.api.UserRegistrationTaisView.UserRegistrationPresenter;

/**
 * @author Timur Yarosh
 *         Date: 21.03.14
 *         Time: 20:40
 */
public class CreateGroupWindow extends Window {

    private CreateGroupWindow window;

    public CreateGroupWindow() {
        super("Новая группа");
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

                    WebApplicationContext ctx = WebApplicationContextUtils
                            .getRequiredWebApplicationContext(VaadinServlet.getCurrent().getServletContext());

                    TextField groupId = new TextField("№ группы");
                    groupId.addValidator(new NotBlankValidator("Поле не может быть пустым"));
                    groupId.setValidationVisible(false);
                    addComponent(groupId);
                    setComponentAlignment(groupId, Alignment.TOP_LEFT);
                    setExpandRatio(groupId, 1);
                    groupId.focus();

                    Button ok = new Button("Создать");
                    addComponent(ok);
                    setComponentAlignment(ok, Alignment.BOTTOM_RIGHT);
                    ok.addClickListener(clickEvent -> {
                        if (groupId.isValid()) {
                            RegistrantService registrantService = ctx.getBean(RegistrantService.class);
                            StudentGroup studentGroup = new StudentGroup(groupId.getValue(), new ArrayList<>());
                            registrantService.createStudentGroup(studentGroup);
                            ComponentFactory componentFactory = (ComponentFactory) VaadinSession.getCurrent()
                                    .getAttribute(SessionKeys.UI_FACTORY);
                            componentFactory.getPresenter(UserRegistrationPresenter.class).update();
                            window.close();
                        } else {
                            Notification.show("Неправильное значение");
                            groupId.focus();
                        }
                    });
                    ok.addStyleName("wide");
                    ok.addStyleName("default");
                    ok.setClickShortcut(ENTER, null);
                }
            });
        }
    }
}
