package ua.org.tees.yarosh.tais.ui.views.admin;

import com.vaadin.data.Validator;
import com.vaadin.data.validator.BeanValidator;
import com.vaadin.ui.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import ua.org.tees.yarosh.tais.core.common.models.Registrant;
import ua.org.tees.yarosh.tais.ui.core.components.BgPanel;
import ua.org.tees.yarosh.tais.ui.core.components.Dash;
import ua.org.tees.yarosh.tais.ui.core.components.DashPanel;
import ua.org.tees.yarosh.tais.ui.core.components.windows.CreateGroupWindow;
import ua.org.tees.yarosh.tais.ui.core.mvp.PresenterBasedVerticalLayoutView;
import ua.org.tees.yarosh.tais.ui.core.mvp.PresenterClass;
import ua.org.tees.yarosh.tais.ui.core.validators.FieldEqualsValidator;

import static ua.org.tees.yarosh.tais.ui.core.UriFragments.Admin.USER_REGISTRATION;

/**
 * @author Timur Yarosh
 *         Date: 22.03.14
 *         Time: 20:44
 */
@PresenterClass(UserRegistrationListener.class)
@Service
@Qualifier(USER_REGISTRATION)
@Scope("prototype")
public class UserRegistrationView extends PresenterBasedVerticalLayoutView<UserRegistrationListener>
        implements UserRegistrationTaisView {

    private TextField login = new TextField();
    private PasswordField password = new PasswordField();
    private PasswordField repeatePassword = new PasswordField();
    private TextField name = new TextField();
    private TextField surname = new TextField();
    private TextField patronymic = new TextField();
    private ComboBox studentGroup = new ComboBox();
    private ComboBox position = new ComboBox();

    public UserRegistrationView() {
        studentGroup.addItem("Новая группа");
        studentGroup.addValueChangeListener(event -> {
            Logger logger = LoggerFactory.getLogger(this.getClass());
            logger.info("Value changed");
            if (event.getProperty().getValue().equals("Новая группа")) {
                logger.info("CreateGroupWindow will be created new");
                getUI().addWindow(new CreateGroupWindow());
            }
        });
        login.focus();
        setUpValidators();
        setSizeFull();
        addStyleName("dashboard-view");
        HorizontalLayout top = new BgPanel("Регистрация нового пользователя");
        addComponent(top);

        HorizontalLayout dash = new Dash();
        addComponent(dash);
        setExpandRatio(dash, 1.5f);

        DashPanel formPanel = new DashPanel();
        formPanel.setCaption("Все поля являются обязательными для заполнения");
        dash.addComponent(formPanel);
        formPanel.setSizeUndefined();

        formPanel.addComponents(createRegistrationForms(), createControls());
        formPanel.setWidth(50, Unit.PERCENTAGE);
        dash.setComponentAlignment(formPanel, Alignment.MIDDLE_CENTER);
    }

    private void setUpValidators() {
        login.addValidator(new BeanValidator(Registrant.class, "login"));
        login.setValidationVisible(false);
        password.addValidator(new BeanValidator(Registrant.class, "password"));
        password.setValidationVisible(false);
        repeatePassword.addValidator(new FieldEqualsValidator(password, "Пароли"));
        repeatePassword.setValidationVisible(false);
        name.addValidator(new BeanValidator(Registrant.class, "name"));
        name.setValidationVisible(false);
        surname.addValidator(new BeanValidator(Registrant.class, "surname"));
        surname.setValidationVisible(false);
        patronymic.addValidator(new BeanValidator(Registrant.class, "patronymic"));
        patronymic.setValidationVisible(false);
    }

    private HorizontalLayout createControls() {
        HorizontalLayout controlsLayout = new HorizontalLayout() {
            {
                setWidth(100, Unit.PERCENTAGE);
                setSpacing(true);
            }
        };

        Button signUpButton = new Button("Зарегистрировать");
        signUpButton.addClickListener(event -> {
            try {
                boolean success = primaryPresenter().createRegistration(login, password,
                        name, surname, patronymic, position, studentGroup);
                if (!success) {
                    Notification.show("Ошибка регистрации");
                } else {
                    clearFields(login, password, repeatePassword, name, surname, patronymic);
                    login.focus();
                }
            } catch (Validator.InvalidValueException e) {
                Notification.show(e.getLocalizedMessage());
            }
        });

        controlsLayout.addComponent(signUpButton);
        controlsLayout.setComponentAlignment(signUpButton, Alignment.BOTTOM_RIGHT);
        return controlsLayout;
    }

    private void clearFields(AbstractTextField... fields) {
        for (AbstractTextField field : fields) {
            field.setValue("");
        }
    }

    private VerticalLayout createRegistrationForms() {
        VerticalLayout registrationDataLayout = new VerticalLayout();

        HorizontalLayout loginLayout = createSingleFormLayout(new Label("Логин"), login);
        HorizontalLayout passwordLayout = createSingleFormLayout(new Label("Пароль"), password);
        HorizontalLayout repeatePasswordLayout = createSingleFormLayout(new Label("Повтор пароля"), repeatePassword);
        HorizontalLayout nameLayout = createSingleFormLayout(new Label("Имя"), name);
        HorizontalLayout surnameLayout = createSingleFormLayout(new Label("Фамилия"), surname);
        HorizontalLayout patronymicLayout = createSingleFormLayout(new Label("Отчество"), patronymic);
        HorizontalLayout studentGroupLayout = createSingleFormLayout(new Label("Группа"), studentGroup);
        HorizontalLayout positionLayout = createSingleFormLayout(new Label("Набор прав"), position);

        registrationDataLayout.addComponents(
                loginLayout,
                passwordLayout,
                repeatePasswordLayout,
                surnameLayout,
                nameLayout,
                patronymicLayout,
                studentGroupLayout,
                positionLayout);
        registrationDataLayout.setWidth(100, Unit.PERCENTAGE);
        return registrationDataLayout;
    }

    private HorizontalLayout createSingleFormLayout(Label description, Component textField) {
        HorizontalLayout layout = new HorizontalLayout() {
            {
                setWidth(100, Unit.PERCENTAGE);
                setSpacing(true);
            }
        };
        layout.addComponents(description, textField);
        layout.setComponentAlignment(textField, Alignment.TOP_RIGHT);
        return layout;
    }

    @Override
    public void setStudentGroupsComboBox(ComboBox studentGroupsComboBox) {
        studentGroupsComboBox.getItemIds().forEach(studentGroup::addItem);
    }

    @Override
    public void setRolesComboBox(ComboBox rolesComboBox) {
        rolesComboBox.getItemIds().forEach(position::addItem);
    }
}
