package ua.org.tees.yarosh.tais.ui.views;

import com.vaadin.event.ShortcutListener;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import ua.org.tees.yarosh.tais.auth.annotations.PermitAll;
import ua.org.tees.yarosh.tais.ui.core.mvp.PresenterBasedVerticalLayoutView;
import ua.org.tees.yarosh.tais.ui.core.mvp.ProducedBy;
import ua.org.tees.yarosh.tais.ui.core.text.UriFragments;

import static com.vaadin.event.ShortcutAction.KeyCode.ENTER;
import static ua.org.tees.yarosh.tais.ui.core.text.Messages.*;
import static ua.org.tees.yarosh.tais.ui.core.text.SessionKeys.REGISTRANT_ID;
import static ua.org.tees.yarosh.tais.ui.core.text.UriFragments.AUTH;
import static ua.org.tees.yarosh.tais.ui.views.LoginTaisView.LoginPresenter;

/**
 * @author Timur Yarosh
 *         Date: 22.03.14
 *         Time: 13:15
 */
@ProducedBy(LoginListener.class)
@Service
@Qualifier(AUTH)
@Scope("prototype")
@PermitAll
public class LoginView extends PresenterBasedVerticalLayoutView<LoginPresenter> implements LoginTaisView {

    public static final Logger LOGGER = LoggerFactory.getLogger(LoginView.class);
    private TextField username;
    private PasswordField password;

    public LoginView() {
        setSizeFull();
        addStyleName("login-layout");
        addStyleName("login-bg");

        LoginPanel loginPanel = new LoginPanel();
        addComponent(loginPanel);
        setComponentAlignment(loginPanel, Alignment.MIDDLE_CENTER);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        primaryPresenter().getHelpManager().addOverlay("TAIS", WELCOME_MESSAGE, "login", UI.getCurrent());
        clearValue(username);
        clearValue(password);
        if (username != null) {
            username.focus();
        }
    }

    private void clearValue(AbstractTextField textField) {
        if (textField != null) {
            textField.setValue("");
        }
    }

    public class LoginPanel extends CssLayout {
        public LoginPanel() {
            addStyleName("login-panel");

            HorizontalLayout labels = new HorizontalLayout();
            labels.setWidth("100%");
            labels.setMargin(true);
            labels.addStyleName("labels");
            addComponent(labels);

            Label welcome = new Label(WELCOME_LABEL);
            welcome.setSizeUndefined();
            welcome.addStyleName("h4");
            labels.addComponent(welcome);
            labels.setComponentAlignment(welcome, Alignment.MIDDLE_LEFT);

            Label title = new Label(APPLICATION_TITLE);
            title.setSizeUndefined();
            title.addStyleName("h2");
            title.addStyleName("light");
            labels.addComponent(title);
            labels.setComponentAlignment(title, Alignment.MIDDLE_RIGHT);

            HorizontalLayout fields = new HorizontalLayout();
            fields.setSpacing(true);
            fields.setMargin(true);
            fields.addStyleName("fields");

            username = new TextField(LOGIN_FIELD_CAPTION);
            username.focus();
            fields.addComponent(username);

            password = new PasswordField(PASSWORD_FIELD_CAPTION);
            fields.addComponent(password);

            Button signIn = new Button(SIGN_IN_BUTTON_CAPTION);
            signIn.addStyleName("default");
            fields.addComponent(signIn);
            fields.setComponentAlignment(signIn, Alignment.BOTTOM_LEFT);

            ShortcutListener enterListener = new ShortcutListener("Sign In", ENTER, null) {
                @Override
                public void handleAction(Object sender, Object target) {
                    signIn.click();
                }
            };

            Label error = new Label(
                    "Неправильное имя пользователя или пароль. <span>Hint: try \"teacher\" values</span>", // todo delete hint!!!
                    ContentMode.HTML);

            signIn.addClickListener(event -> {
                if (username.getValue() != null
                        && password.getValue() != null
                        && primaryPresenter().login(username.getValue(), password.getValue())) {
//                    ------


                    // ------
//                    signIn.removeShortcutListener(enterListener);
                    removeComponent(error);
                    LOGGER.info("Authorization success, navigating to teacher dashboard");

                    VaadinSession.getCurrent().setAttribute(REGISTRANT_ID, username.getValue());
                    getUI().getNavigator().navigateTo(UriFragments.Admin.USER_REGISTRATION);
                } else {
                    if (getComponentCount() > 2) {
                        // Remove the previous error message
                        removeComponent(getComponent(2));
                    }
                    // Add new error message
                    error.addStyleName("error");
                    error.setSizeUndefined();
                    error.addStyleName("light");
                    // Add animation
                    error.addStyleName("v-animate-reveal");
                    addComponent(error);
                    username.focus();
                }
            });
            signIn.addShortcutListener(enterListener);
            addComponent(fields);
        }
    }
}
