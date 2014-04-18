package ua.org.tees.yarosh.tais.ui.views.admin;

import com.vaadin.data.Validatable;
import com.vaadin.data.Validator;
import com.vaadin.data.validator.BeanValidator;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Qualifier;
import ua.org.tees.yarosh.tais.auth.api.annotations.PermitRoles;
import ua.org.tees.yarosh.tais.core.common.models.Registrant;
import ua.org.tees.yarosh.tais.ui.components.layouts.DashPanel;
import ua.org.tees.yarosh.tais.ui.components.layouts.DashboardView;
import ua.org.tees.yarosh.tais.ui.core.UIFactory;
import ua.org.tees.yarosh.tais.ui.core.mvp.PresentedBy;
import ua.org.tees.yarosh.tais.ui.core.mvp.TaisView;
import ua.org.tees.yarosh.tais.ui.core.validators.FieldEqualsValidator;
import ua.org.tees.yarosh.tais.ui.core.validators.NotBlankValidator;
import ua.org.tees.yarosh.tais.ui.views.admin.api.UserRegistrationTaisView;

import static ua.org.tees.yarosh.tais.core.common.dto.Roles.ADMIN;
import static ua.org.tees.yarosh.tais.ui.core.api.DataBinds.UriFragments.Admin.USER_REGISTRATION;
import static ua.org.tees.yarosh.tais.ui.views.admin.api.UserRegistrationTaisView.UserRegistrationPresenter;

/**
 * @author Timur Yarosh
 *         Date: 22.03.14
 *         Time: 20:44
 */
@PresentedBy(UserRegistrationPresenter.class)
@TaisView("Регистрация нового пользователя")
@Qualifier(USER_REGISTRATION)
@PermitRoles(ADMIN)
public class UserRegistrationView extends DashboardView implements UserRegistrationTaisView {

    private TextField login = new TextField();
    private PasswordField password = new PasswordField();
    private PasswordField repeatPassword = new PasswordField();
    private TextField name = new TextField();
    private TextField surname = new TextField();
    private TextField patronymic = new TextField();
    private TextField email = new TextField();
    private ComboBox studentGroupComboBox = new ComboBox();
    private ComboBox position = new ComboBox();

    @Override
    public void update() {
        studentGroupComboBox.removeAllItems();
        UserRegistrationPresenter presenter = UIFactory.getCurrent()
                .getRelativePresenter(this, UserRegistrationPresenter.class);
        presenter.listStudentGroups().forEach(studentGroupComboBox::addItem);
        position.removeAllItems();
        presenter.listRoles().forEach(position::addItem);
    }

    public UserRegistrationView() {
        super();
        login.focus();
        setUpValidators();

        DashPanel formPanel = addDashPanel(null, null);
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
        repeatPassword.addValidator(new FieldEqualsValidator(password, "Пароли"));
        repeatPassword.setValidationVisible(false);
        name.addValidator(new BeanValidator(Registrant.class, "name"));
        name.setValidationVisible(false);
        surname.addValidator(new BeanValidator(Registrant.class, "surname"));
        surname.setValidationVisible(false);
        patronymic.addValidator(new BeanValidator(Registrant.class, "patronymic"));
        patronymic.setValidationVisible(false);
        email.addValidator(new BeanValidator(Registrant.class, "email"));
        email.setValidationVisible(false);
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
                if (isValid(login, password, repeatPassword, name, surname,
                        patronymic, email, position, studentGroupComboBox)) {
                    boolean success = UIFactory.getCurrent()
                            .getRelativePresenter(this, UserRegistrationPresenter.class)
                            .createRegistration(login, password, name, surname,
                                    patronymic, email, position, studentGroupComboBox);
                    if (!success) {
                        Notification.show("Логин занят");
                    } else {
                        clearFields(login, password, repeatPassword, name, surname, patronymic, email);
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
        HorizontalLayout repeatePasswordLayout = createSingleFormLayout(new Label("Повтор пароля"), repeatPassword);
        HorizontalLayout nameLayout = createSingleFormLayout(new Label("Имя"), name);
        HorizontalLayout surnameLayout = createSingleFormLayout(new Label("Фамилия"), surname);
        HorizontalLayout patronymicLayout = createSingleFormLayout(new Label("Отчество"), patronymic);
        HorizontalLayout emailLayout = createSingleFormLayout(new Label("Email"), email);
        HorizontalLayout studentGroupLayout = createSingleFormLayout(new Label("Группа"), studentGroupComboBox);
        HorizontalLayout positionLayout = createSingleFormLayout(new Label("Роль"), position);

        registrationDataLayout.addComponents(
                loginLayout,
                passwordLayout,
                repeatePasswordLayout,
                surnameLayout,
                nameLayout,
                patronymicLayout,
                emailLayout,
                studentGroupLayout,
                positionLayout);
        registrationDataLayout.setWidth(100, Unit.PERCENTAGE);
        return registrationDataLayout;
    }

    private HorizontalLayout createSingleFormLayout(Label description, Component component) {
        HorizontalLayout layout = new HorizontalLayout() {
            {
                setWidth(100, Unit.PERCENTAGE);
                setSpacing(true);
            }
        };
        layout.addComponents(description, component);
        layout.setComponentAlignment(component, Alignment.TOP_RIGHT);
        return layout;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        update();
    }
}
