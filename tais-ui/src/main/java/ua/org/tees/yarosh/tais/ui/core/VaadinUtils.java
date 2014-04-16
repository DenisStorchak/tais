package ua.org.tees.yarosh.tais.ui.core;

import com.vaadin.data.Validatable;
import com.vaadin.server.FileDownloader;
import com.vaadin.server.StreamResource;
import com.vaadin.ui.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import static com.vaadin.server.VaadinService.getCurrentResponse;

/**
 * @author Timur Yarosh
 *         Date: 29.03.14
 *         Time: 19:19
 */
public class VaadinUtils {

    private UI ui;

    public VaadinUtils(UI ui) {
        this.ui = ui;
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

    public void saveCookie(String name, String value) {
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(15552000);
        cookie.setPath("/");
        ui.access(() -> getCurrentResponse().addCookie(cookie));
    }

    public void deleteCookie(String name) {
        ui.access(() -> {
            Cookie cookie = new Cookie(name, "");
            cookie.setPath("/");
            cookie.setMaxAge(0);
            getCurrentResponse().addCookie(cookie);
        });
    }

//    public Cookie getCookie(String name) {
//        VaadinRequest currentRequest = getCurrentRequest();
//        if (currentRequest != null) {
//            for (Cookie cookie : currentRequest.getCookies()) {
//                if (cookie.getName().equals(name)) {
//                    return cookie;
//                }
//            }
//        }
//        return new Cookie(name, "");
//    }

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
