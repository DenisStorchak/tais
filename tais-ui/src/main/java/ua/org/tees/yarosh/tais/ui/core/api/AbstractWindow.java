package ua.org.tees.yarosh.tais.ui.core.api;

import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import static com.vaadin.event.ShortcutAction.KeyCode.ESCAPE;

public abstract class AbstractWindow extends Window implements Initable {

    public AbstractWindow() {
        super();
        setModal(true);
        setClosable(true);
        setResizable(false);
        addStyleName("edit-dashboard");
        setCloseShortcut(ESCAPE);
    }

    private VerticalLayout createVerticalLayout() {
        VerticalLayout contentLayout = new VerticalLayout();
        contentLayout.setMargin(true);
        contentLayout.setSpacing(true);
        contentLayout.addStyleName("footer");
        contentLayout.setWidth("100%");
        fillOutLayout(contentLayout);
        return contentLayout;
    }

    protected abstract void fillOutLayout(VerticalLayout contentLayout);

    @Override
    public void init() {
        TaisWindow taisWindow = getClass().getAnnotation(TaisWindow.class);
        setCaption(taisWindow.value());

        setContent(createVerticalLayout());
    }

    public void close() {
        this.close();
    }
}
