package ua.org.tees.yarosh.tais.ui.views.common;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Qualifier;
import ua.org.tees.yarosh.tais.auth.api.annotations.PermitAll;
import ua.org.tees.yarosh.tais.ui.core.mvp.TaisView;

import static ua.org.tees.yarosh.tais.ui.core.api.DataBinds.UriFragments.ACCESS_DENIED;

@PermitAll
@TaisView
@Qualifier(ACCESS_DENIED)
public class AccessDeniedView extends VerticalLayout implements View {
    public AccessDeniedView() {
        setSizeFull();
        addStyleName("login-layout");
        addStyleName("login-bg");

        Label pageNotFoundLabel =
                new Label("<center><h1>Доступ запрещен</h1></center>",
                        ContentMode.HTML);
        addComponent(pageNotFoundLabel);
        setComponentAlignment(pageNotFoundLabel, Alignment.MIDDLE_CENTER);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

    }
}
