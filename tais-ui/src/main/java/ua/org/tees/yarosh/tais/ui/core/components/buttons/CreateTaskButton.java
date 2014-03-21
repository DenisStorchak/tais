package ua.org.tees.yarosh.tais.ui.core.components.buttons;

import com.vaadin.ui.Button;
import ua.org.tees.yarosh.tais.ui.core.Messages;
import ua.org.tees.yarosh.tais.ui.roles.teacher.windows.CreateTaskWindow;

/**
 * @author Timur Yarosh
 *         Date: 22.03.14
 *         Time: 0:12
 */
public class CreateTaskButton extends Button {
    public CreateTaskButton() {
        addStyleName("icon-edit");
        addStyleName("icon-only");
        setDescription(Messages.CREATE_TASK_BUTTON_DESCRIPTION);
        addClickListener(clickEvent -> getUI().addWindow(new CreateTaskWindow()));
    }
}
