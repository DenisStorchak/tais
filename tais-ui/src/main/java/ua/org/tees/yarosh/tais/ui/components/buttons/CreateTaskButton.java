package ua.org.tees.yarosh.tais.ui.components.buttons;

import com.vaadin.ui.Button;
import ua.org.tees.yarosh.tais.ui.components.windows.CreateTaskWindow;
import ua.org.tees.yarosh.tais.ui.core.Messages;

/**
 * @author Timur Yarosh
 *         Date: 22.03.14
 *         Time: 0:12
 */
public class CreateTaskButton extends Button {
    public CreateTaskButton() {
        addStyleName("icon-doc-new");
        addStyleName("icon-only");
        setDescription(Messages.CREATE_TASK_BUTTON_DESCRIPTION);
        addClickListener(clickEvent -> getUI().addWindow(new CreateTaskWindow()));
    }
}
