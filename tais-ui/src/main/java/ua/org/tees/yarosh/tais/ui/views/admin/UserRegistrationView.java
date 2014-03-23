package ua.org.tees.yarosh.tais.ui.views.admin;

import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import ua.org.tees.yarosh.tais.ui.core.components.BgPanel;
import ua.org.tees.yarosh.tais.ui.core.components.Dash;
import ua.org.tees.yarosh.tais.ui.core.components.DashPanel;
import ua.org.tees.yarosh.tais.ui.core.mvp.AbstractPresenter;
import ua.org.tees.yarosh.tais.ui.core.mvp.PresenterClass;

import java.util.LinkedList;

/**
 * @author Timur Yarosh
 *         Date: 22.03.14
 *         Time: 20:44
 */
@PresenterClass(UserRegistrationListener.class)
public class UserRegistrationView extends VerticalLayout implements UserRegistrationTaisView {

    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final String STYLE_ATTENTION = "icon-attention";
    private static final String STYLE_OK = "icon-ok";
    private LinkedList<UserRegistrationListener> presenters = new LinkedList<>();
    private boolean loginValid = false;
    private TextField login = new TextField();
    private PasswordField password = new PasswordField();
    private PasswordField repeatePassword = new PasswordField();
    private TextField name = new TextField();
    private TextField surname = new TextField();
    private TextField patronymic = new TextField();
    private TextField studentGroup = new TextField();
    private TextField position = new TextField();

    public UserRegistrationView() {
        setSizeFull();
        addStyleName("dashboard-view");
        HorizontalLayout top = new BgPanel("Регистрация нового пользователя");
        addComponent(top);

        HorizontalLayout dash = new Dash();
        addComponent(dash);
        setExpandRatio(dash, 1.5f);

        DashPanel formPanel = new DashPanel();
        dash.addComponent(formPanel);
        formPanel.setSizeUndefined();

        formPanel.addComponents(createRegistrationForms(), createControls());
        formPanel.setWidth("50%");
        dash.setComponentAlignment(formPanel, Alignment.MIDDLE_CENTER);
    }

    private HorizontalLayout createControls() {
        HorizontalLayout controlsLayout = new HorizontalLayout() {
            {
                setWidth(100, Unit.PERCENTAGE);
                setSpacing(true);
            }
        };

        Button signUpButton = new Button("Зарегистрировать");
        signUpButton.addClickListener(event -> presenters.getFirst().createRegistration(
                login, password, name, surname, patronymic, position, studentGroup
        ));

        controlsLayout.addComponent(signUpButton);
        controlsLayout.setComponentAlignment(signUpButton, Alignment.BOTTOM_RIGHT);
        return controlsLayout;
    }

    private VerticalLayout createRegistrationForms() {
        VerticalLayout registrationDataLayout = new VerticalLayout();
        registrationDataLayout.setCaption("Все поля являются обязательными для заполнения");

        HorizontalLayout loginLayout = createSingleFormLayout(new Label("Логин"), login);
        HorizontalLayout passwordLayout = createSingleFormLayout(new Label("Пароль"), password);
        HorizontalLayout repeatePasswordLayout = createSingleFormLayout(new Label("Повтор пароля"), repeatePassword);
        HorizontalLayout nameLayout = createSingleFormLayout(new Label("Имя"), name);
        HorizontalLayout surnameLayout = createSingleFormLayout(new Label("Фамилия"), surname);
        HorizontalLayout patronymicLayout = createSingleFormLayout(new Label("Отчество"), patronymic);
        HorizontalLayout studentGroupLayout = createSingleFormLayout(new Label("Группа"), studentGroup);
        HorizontalLayout positionLayout = createSingleFormLayout(new Label("Должность"), position);

        registrationDataLayout.addComponents(
                loginLayout,
                passwordLayout,
                repeatePasswordLayout,
                nameLayout,
                surnameLayout,
                patronymicLayout,
                studentGroupLayout,
                positionLayout);
        return registrationDataLayout;
    }

    private HorizontalLayout createSingleFormLayout(Label description, AbstractTextField textField) {
        HorizontalLayout layout = new HorizontalLayout() {
            {
                setWidth(100, Unit.PERCENTAGE);
                setSpacing(true);
            }
        };
        layout.addComponents(description, textField);
        textField.setSizeFull();
        return layout;
    }

    @Override
    public void addPresenter(AbstractPresenter presenter) {
        presenters.add((UserRegistrationListener) presenter);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

    }
}
