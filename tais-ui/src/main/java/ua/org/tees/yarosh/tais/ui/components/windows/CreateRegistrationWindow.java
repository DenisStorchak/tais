package ua.org.tees.yarosh.tais.ui.components.windows;

import com.vaadin.data.Validatable;
import com.vaadin.data.Validator;
import com.vaadin.data.validator.BeanValidator;
import com.vaadin.ui.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import ua.org.tees.yarosh.tais.core.common.models.Registrant;
import ua.org.tees.yarosh.tais.ui.core.UIFactoryAccessor;
import ua.org.tees.yarosh.tais.ui.core.validators.FieldEqualsValidator;
import ua.org.tees.yarosh.tais.ui.core.validators.NotBlankValidator;

import static com.vaadin.event.ShortcutAction.KeyCode.ESCAPE;
import static ua.org.tees.yarosh.tais.ui.views.admin.api.UserRegistrationTaisView.UserRegistrationPresenter;

@Service
@Scope("prototype")
public class CreateRegistrationWindow extends Window {

    private Window window;

    public CreateRegistrationWindow() {
        super("Регистрация нового пользователя");
        window = this;
        setModal(true);
        setClosable(true);
        setResizable(false);
        addStyleName("edit-dashboard");
        setContent(new CreateRegistrationWindowContent());
    }

    private class CreateRegistrationWindowContent extends VerticalLayout {
        public CreateRegistrationWindowContent() {
            addComponent(new VerticalLayout() {
                private TextField login = new TextField();
                private PasswordField password = new PasswordField();
                private PasswordField repeatPassword = new PasswordField();
                private TextField name = new TextField();
                private TextField surname = new TextField();
                private TextField patronymic = new TextField();
                private TextField email = new TextField();
                private ComboBox studentGroupComboBox = new ComboBox();
                private ComboBox position = new ComboBox();

                {
                    setMargin(true);
                    setSpacing(true);
                    addStyleName("footer");
                    setWidth("100%");
                    setCloseShortcut(ESCAPE);
                    login.focus();
                    setUpValidators();

                    UserRegistrationPresenter presenter = UIFactoryAccessor.getCurrent()
                            .getPresenter(UserRegistrationPresenter.class);
                    presenter.listStudentGroups().forEach(studentGroupComboBox::addItem);
                    position.removeAllItems();
                    presenter.listRoles().forEach(position::addItem);
                    addComponents(createRegistrationForms(), createControls());
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
                                boolean success = UIFactoryAccessor.getCurrent()
                                        .getPresenter(UserRegistrationPresenter.class)
                                        .createRegistration(login, password, name, surname,
                                                patronymic, email, position, studentGroupComboBox);
                                if (!success) {
                                    Notification.show("Логин занят");
                                } else {
                                    window.close();
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
            });
        }
    }
}
