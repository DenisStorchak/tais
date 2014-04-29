package ua.org.tees.yarosh.tais.ui.views.common;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import ua.org.tees.yarosh.tais.auth.api.annotations.PermitRoles;
import ua.org.tees.yarosh.tais.ui.core.api.PresenterUnnecessary;

import static ua.org.tees.yarosh.tais.core.common.dto.Roles.*;

@PermitRoles({ADMIN, TEACHER, STUDENT})
@PresenterUnnecessary
public class PageNotFoundView extends VerticalLayout implements View {
    public PageNotFoundView() {
        setSizeFull();
        addStyleName("login-layout");
        addStyleName("login-bg");

        Label pageNotFoundLabel =
                new Label("<center><h1>Страница не найдена, возможно вы опечатались при наборе URL?</h1></center>",
                        ContentMode.HTML);
        addComponent(pageNotFoundLabel);
        setComponentAlignment(pageNotFoundLabel, Alignment.MIDDLE_CENTER);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

    }
}
