package ua.org.tees.yarosh.tais.ui.components.layouts;

import com.vaadin.ui.CssLayout;

public class RootLayout extends CssLayout {

    public RootLayout(CommonComponent commonComponent) {
        addStyleName("root");
        setSizeFull();
        addComponent(commonComponent);
    }
}
