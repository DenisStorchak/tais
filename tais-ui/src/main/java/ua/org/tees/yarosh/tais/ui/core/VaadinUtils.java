package ua.org.tees.yarosh.tais.ui.core;

import com.vaadin.data.Validatable;
import com.vaadin.server.FileDownloader;
import com.vaadin.server.StreamResource;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * @author Timur Yarosh
 *         Date: 29.03.14
 *         Time: 19:19
 */
@SuppressWarnings("unchecked")
public class VaadinUtils {

    public static final Logger log = LoggerFactory.getLogger(VaadinUtils.class);
    private static final int COOKIES_EXPIRY = 15552000;
    private UI ui;

    private VaadinUtils(UI ui) {
        this.ui = ui;
    }

    public static VaadinUtils extendedUtils(UI ui) {
        if (ui == null) {
            throw new IllegalArgumentException("ui must be not null");
        }
        return new VaadinUtils(ui);
    }

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

    public static boolean validOrThrow(Validatable... validatables) {
        for (Validatable validatable : validatables) {
            if (!validatable.isValid()) {
                ((Component.Focusable) validatable).focus();
                throw new IllegalArgumentException("Value isn't valid");
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

    public static VerticalLayout createSingleFormVerticalLayout(Component leftComponent, Component rightComponent) {
        VerticalLayout layout = new VerticalLayout() {
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

    public static void store(String key, Object object) {
        VaadinSession.getCurrent().setAttribute(key, object);
    }

    public static <E> E get(String key, Class<? extends E> clazz) {
        return clazz.cast(VaadinSession.getCurrent().getAttribute(key));
    }

    public static void transformToIconOnlyButton(String description,
                                                 String icon,
                                                 Button.ClickListener listener,
                                                 Button button) {
        button.setDescription(description);
        button.addStyleName(icon);
        button.addStyleName("icon-only");
        button.addClickListener(listener);
    }

    public static void extendDownloadButton(File source, Button button) {
        new FileDownloader(new StreamResource(new PayloadSource(source), source.getName())).extend(button);
    }

    public static class PayloadSource implements StreamResource.StreamSource {

        private static final Logger log = LoggerFactory.getLogger(PayloadSource.class);

        private File source;

        public PayloadSource(File source) {
            this.source = source;
        }

        @Override
        public InputStream getStream() {
            try {
                return new FileInputStream(source);
            } catch (FileNotFoundException e) {
                log.error("File not found. [REASON: {}]", e);
            }
            return null;
        }
    }
}
