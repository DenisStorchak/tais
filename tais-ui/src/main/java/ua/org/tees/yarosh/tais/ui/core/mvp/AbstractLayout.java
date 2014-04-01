package ua.org.tees.yarosh.tais.ui.core.mvp;

import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;
import ua.org.tees.yarosh.tais.ui.core.UIFactory;

public abstract class AbstractLayout extends VerticalLayout {
    private UIFactory uiFactory;

    public UIFactory getUIFactory() {
        return uiFactory;
    }

    @Autowired
    public void setUIFactory(UIFactory uiFactory) {
        this.uiFactory = uiFactory;
    }
}
