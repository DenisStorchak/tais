package ua.org.tees.yarosh.tais.ui.components.windows;

import com.vaadin.ui.*;

import static com.vaadin.event.ShortcutAction.KeyCode.ENTER;
import static com.vaadin.event.ShortcutAction.KeyCode.ESCAPE;
import static ua.org.tees.yarosh.tais.ui.core.constants.Messages.*;

/**
 * @author Timur Yarosh
 *         Date: 21.03.14
 *         Time: 20:40
 */
public class CreateTaskWindow extends Window {

    private CreateTaskWindow window;

    public CreateTaskWindow() {
        super(CREATE_TASK_WINDOW_TITLE);
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

                    Button manualTaskOne = new Button(MANUAL_TASK);
                    addComponent(manualTaskOne);
                    manualTaskOne.addClickListener(clickEvent -> window.close());     //todo manual task creation window
                    manualTaskOne.addStyleName("wide");
                    setComponentAlignment(manualTaskOne, Alignment.TOP_LEFT);
                    setExpandRatio(manualTaskOne, 1);

                    Button questionsSuiteOne = new Button(QUESTIONS_SUITE_TITLE);
                    addComponent(questionsSuiteOne);
                    questionsSuiteOne.addClickListener(clickEvent -> window.close()); //todo suite creation window
                    questionsSuiteOne.addStyleName("wide");
                    questionsSuiteOne.addStyleName("default");
                    questionsSuiteOne.setClickShortcut(ENTER, null);
                }
            });
        }
    }
}
