package ua.org.tees.yarosh.tais.ui.views.admin;

import com.vaadin.data.Validatable;
import com.vaadin.data.Validator;
import com.vaadin.data.validator.BeanValidator;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import ua.org.tees.yarosh.tais.core.common.models.Registrant;
import ua.org.tees.yarosh.tais.ui.components.BgPanel;
import ua.org.tees.yarosh.tais.ui.components.Dash;
import ua.org.tees.yarosh.tais.ui.components.DashPanel;
import ua.org.tees.yarosh.tais.ui.components.windows.CreateGroupWindow;
import ua.org.tees.yarosh.tais.ui.core.mvp.PresenterBasedVerticalLayoutView;
import ua.org.tees.yarosh.tais.ui.core.mvp.ProducedBy;
import ua.org.tees.yarosh.tais.ui.core.validators.FieldEqualsValidator;
import ua.org.tees.yarosh.tais.ui.core.validators.NotBlankValidator;

import static ua.org.tees.yarosh.tais.ui.core.UriFragments.Admin.USER_REGISTRATION;
import static ua.org.tees.yarosh.tais.ui.views.admin.UserRegistrationTaisView.UserRegistrationPresenter;

/**
 * @author Timur Yarosh
 *         Date: 22.03.14
 *         Time: 20:44
 */
@ProducedBy(UserRegistrationListener.class)
@Service
@Qualifier(USER_REGISTRATION)
@Scope("prototype")
public class UserRegistrationView extends PresenterBasedVerticalLayoutView<UserRegistrationPresenter>
        implements UserRegistrationTaisView {

    private TextField login = new TextField();
    private PasswordField password = new PasswordField();
    private PasswordField repeatePassword = new PasswordField();
    private TextField name = new TextField();
    private TextField surname = new TextField();
    private TextField patronymic = new TextField();
    private ComboBox studentGroupComboBox = new ComboBox();
    private ComboBox position = new ComboBox();

    public UserRegistrationView() {
        login.focus();
        setUpValidators();
        setSizeFull();
        addStyleName("dashboard-view");
        HorizontalLayout top = new BgPanel("Регистрация нового пользователя");
        addComponent(top);

        Button createGroup = new Button("Новая группа");
        createGroup.addStyleName("icon-only");
        createGroup.addStyleName("icon-doc-new"); // todo set correct icon
        createGroup.setDescription("Создать новую группу");
        createGroup.addClickListener(clickEvent -> getUI().addWindow(new CreateGroupWindow(primaryPresenter())));
        top.addComponent(createGroup);
        top.setComponentAlignment(createGroup, Alignment.MIDDLE_LEFT);

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
        studentGroupComboBox.addValidator(new NotBlankValidator("Некорректно заполнено поле"));
        studentGroupComboBox.setValidationVisible(false);
        position.addValidator(new NotBlankValidator("Некорректно заполнено поле"));
        position.setValidationVisible(false);
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
                if (isValid(login, password, repeatePassword, name, surname, patronymic, position, studentGroupComboBox)) {
                    boolean success = primaryPresenter().createRegistration(login, password,
                            name, surname, patronymic, position, studentGroupComboBox);
                    if (!success) {
                        Notification.show("Ошибка регистрации");
                    } else {
                        clearFields(login, password, repeatePassword, name, surname, patronymic);
                        login.focus();
                    }
                } else {
                    Notification.show("Не все поля правильно заполнены");
                }
            } catch (Validator.InvalidValueException e) {
                Notification.show(e.getLocalizedMessage());
            }
        });

        controlsLayout.addComponent(signUpButton);
        controlsLayout.setComponentAlignment(signUpButton, Alignment.BOTTOM_RIGHT);
        return controlsLayout;
    }

    private boolean isValid(Validatable... validatables) {
        for (Validatable validatable : validatables) {
            if (!validatable.isValid()) {
                ((Focusable) validatable).focus();
                return false;
            }
        }
        return true;
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
        HorizontalLayout studentGroupLayout = createSingleFormLayout(new Label("Группа"), studentGroupComboBox);
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
        studentGroupsComboBox.getItemIds().forEach(studentGroupComboBox::addItem);
    }

    @Override
    public void setRolesComboBox(ComboBox rolesComboBox) {
        rolesComboBox.getItemIds().forEach(position::addItem);
    }
}
