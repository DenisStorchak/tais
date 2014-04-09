package ua.org.tees.yarosh.tais.ui.views.common;

import com.vaadin.event.ShortcutListener;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Qualifier;
import ua.org.tees.yarosh.tais.auth.annotations.PermitAll;
import ua.org.tees.yarosh.tais.core.common.models.Registrant;
import ua.org.tees.yarosh.tais.ui.core.SessionFactory;
import ua.org.tees.yarosh.tais.ui.core.ViewResolver;
import ua.org.tees.yarosh.tais.ui.core.mvp.PresentedBy;
import ua.org.tees.yarosh.tais.ui.core.mvp.TaisView;
import ua.org.tees.yarosh.tais.ui.views.common.api.LoginTais;

import static com.vaadin.event.ShortcutAction.KeyCode.ENTER;
import static ua.org.tees.yarosh.tais.ui.core.DataBinds.Messages.*;
import static ua.org.tees.yarosh.tais.ui.core.DataBinds.SessionKeys.REGISTRANT_ID;
import static ua.org.tees.yarosh.tais.ui.core.DataBinds.UriFragments.AUTH;
import static ua.org.tees.yarosh.tais.ui.views.common.api.LoginTais.LoginPresenter;

/**
 * @author Timur Yarosh
 *         Date: 22.03.14
 *         Time: 13:15
 */
@PresentedBy(LoginPresenter.class)
@TaisView
@Qualifier(AUTH)
@PermitAll
public class Login extends VerticalLayout implements LoginTais {

    private TextField username;
    private PasswordField password;
    private Login instance;

    public Login() {
        instance = this;
        setSizeFull();
        addStyleName("login-layout");
        addStyleName("login-bg");

        LoginPanel loginPanel = new LoginPanel();
        addComponent(loginPanel);
        setComponentAlignment(loginPanel, Alignment.MIDDLE_CENTER);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        SessionFactory.getCurrent().getHelpManager().addOverlay("TAIS", WELCOME_MESSAGE, "login", UI.getCurrent());
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
                    "<center>Неправильное имя пользователя или пароль.<br />" +
                            "За регистрацией обращайтесь на кафедру ТЭЭС</center>",
                    ContentMode.HTML
            );

            signIn.addClickListener(event -> {
                Registrant login = SessionFactory.getCurrent().getRelativePresenter(instance, LoginPresenter.class)
                        .login(username.getValue(), password.getValue());
                if (username.getValue() != null
                        && password.getValue() != null
                        && login != null) {
                    removeComponent(error);

                    // save user to session
                    // todo remove line (use cookie instead)
                    VaadinSession.getCurrent().setAttribute(REGISTRANT_ID, username.getValue());

                    getUI().getNavigator().navigateTo(ViewResolver.resolveDefaultView(login));
                } else {
                    if (getComponentCount() > 2) {
                        removeComponent(getComponent(2));
                    }
                    error.addStyleName("error");
                    error.setSizeUndefined();
                    error.addStyleName("light");
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
