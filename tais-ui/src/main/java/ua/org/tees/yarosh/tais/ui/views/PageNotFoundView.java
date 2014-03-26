package ua.org.tees.yarosh.tais.ui.views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class PageNotFoundView extends VerticalLayout implements View {
    public PageNotFoundView() {
        setSizeFull();
        addStyleName("login-layout");
        addStyleName("login-bg");

        Label pageNotFoundLabel =
                new Label("<center><h1>Страница не найдена, возможно вы опечатались в наборе URL?</h1></center>",
                        ContentMode.HTML);
        addComponent(pageNotFoundLabel);
        setComponentAlignment(pageNotFoundLabel, Alignment.MIDDLE_CENTER);

        Page.getCurrent().setTitle("Страница не найдена");
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

    }
}
