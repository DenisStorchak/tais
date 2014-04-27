package ua.org.tees.yarosh.tais.ui.components.windows;

import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import ua.org.tees.yarosh.tais.ui.core.api.AbstractWindow;
import ua.org.tees.yarosh.tais.ui.core.api.TaisWindow;

import static com.vaadin.event.ShortcutAction.KeyCode.ENTER;
import static com.vaadin.ui.Alignment.BOTTOM_RIGHT;

/**
 * @author Timur Yarosh
 *         Date: 27.04.14
 *         Time: 14:11
 */
@TaisWindow("Профиль не найден")
public class RegistrantNotFoundWindow extends AbstractWindow {

    private Label errMessage = new Label("Профиль не найден");
    private Button okButton = new Button("Ок");

    @Override
    public void init() {
        super.init();

        okButton.setClickShortcut(ENTER);
        okButton.addClickListener(e -> close());
    }

    @Override
    protected void fillOutLayout(VerticalLayout contentLayout) {
        contentLayout.addComponent(errMessage);
        contentLayout.addComponent(okButton);
        contentLayout.setComponentAlignment(okButton, BOTTOM_RIGHT);
    }
}
