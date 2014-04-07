package ua.org.tees.yarosh.tais.ui.core;

import com.vaadin.data.Validatable;
import com.vaadin.ui.*;

/**
 * @author Timur Yarosh
 *         Date: 29.03.14
 *         Time: 19:19
 */
public abstract class VaadinUtils {

    public static void setSizeUndefined(Component... components) {
        for (Component component : components) {
            component.setSizeUndefined();
        }
    }

    public static void setValidationVisible(boolean visible, AbstractField... fields) {
        for (AbstractField field : fields) {
            field.setValidationVisible(visible);
        }
    }

    public static void setSizeFull(Component... components) {
        for (Component component : components) {
            component.setSizeFull();
        }
    }

    public static boolean isValid(Validatable... validatables) {
        for (Validatable validatable : validatables) {
            if (!validatable.isValid()) {
                ((Component.Focusable) validatable).focus();
                return false;
            }
        }
        return true;
    }

    public static void clearFields(AbstractTextField... fields) {
        for (AbstractTextField field : fields) {
            field.setValue("");
        }
    }

    public static HorizontalLayout createSingleFormLayout(Component leftComponent, Component rightComponent) {
        HorizontalLayout layout = new HorizontalLayout() {
            {
                setWidth(100, Unit.PERCENTAGE);
                setSpacing(true);
            }
        };
        if (leftComponent != null) layout.addComponent(leftComponent);
        if (rightComponent != null) layout.addComponent(rightComponent);
        layout.setComponentAlignment(rightComponent, Alignment.TOP_RIGHT);
        return layout;
    }
}
