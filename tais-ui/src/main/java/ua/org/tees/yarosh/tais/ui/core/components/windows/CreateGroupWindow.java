package ua.org.tees.yarosh.tais.ui.core.components.windows;

import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;
import ua.org.tees.yarosh.tais.core.common.models.StudentGroup;
import ua.org.tees.yarosh.tais.core.user.mgmt.api.service.RegistrantService;
import ua.org.tees.yarosh.tais.ui.configuration.SpringContextHelper;
import ua.org.tees.yarosh.tais.ui.views.admin.UserRegistrationListener;

import java.util.ArrayList;

import static com.vaadin.event.ShortcutAction.KeyCode.ENTER;
import static com.vaadin.event.ShortcutAction.KeyCode.ESCAPE;
import static java.lang.Integer.valueOf;

/**
 * @author Timur Yarosh
 *         Date: 21.03.14
 *         Time: 20:40
 */
public class CreateGroupWindow extends Window {

    private final UserRegistrationListener userRegistrationListener;
    private CreateGroupWindow window;
    private String createdGroup;

    public CreateGroupWindow(UserRegistrationListener userRegistrationListener) {
        super("Новая группа");
        window = this;
        setModal(true);
        setClosable(true);
        setResizable(false);
        addStyleName("edit-dashboard");
        setContent(new CreateTaskWindowContent());
        this.userRegistrationListener = userRegistrationListener;
    }

    public String getCreatedGroup() {
        return createdGroup;
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

                    SpringContextHelper ctx = new SpringContextHelper(VaadinServlet.getCurrent().getServletContext());

                    TextField groupId = new TextField("№ группы");
                    addComponent(groupId);
                    setComponentAlignment(groupId, Alignment.TOP_LEFT);
                    setExpandRatio(groupId, 1);
                    groupId.focus();

                    Button ok = new Button("Создать");
                    addComponent(ok);
                    setComponentAlignment(ok, Alignment.BOTTOM_RIGHT);
                    ok.addClickListener(clickEvent -> {
                        createdGroup = groupId.getValue();
                        RegistrantService registrantService = ctx.getBean(RegistrantService.class);
                        StudentGroup studentGroup = new StudentGroup(valueOf(groupId.getValue()), new ArrayList<>());
                        registrantService.addStudentGroup(studentGroup);
                        userRegistrationListener.initView();
                        window.close();
                    });
                    ok.addStyleName("wide");
                    ok.addStyleName("default");
                    ok.setClickShortcut(ENTER, null);
                }
            });
        }
    }
}
