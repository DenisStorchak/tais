package ua.org.tees.yarosh.tais.ui.views.common;

import com.vaadin.data.validator.BeanValidator;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import ua.org.tees.yarosh.tais.auth.annotations.PermitRoles;
import ua.org.tees.yarosh.tais.core.common.models.Registrant;
import ua.org.tees.yarosh.tais.core.common.models.StudentGroup;
import ua.org.tees.yarosh.tais.ui.components.layouts.DashPanel;
import ua.org.tees.yarosh.tais.ui.components.layouts.DashboardView;
import ua.org.tees.yarosh.tais.ui.core.mvp.PresentedBy;
import ua.org.tees.yarosh.tais.ui.core.mvp.TaisView;
import ua.org.tees.yarosh.tais.ui.core.validators.FieldEqualsValidator;
import ua.org.tees.yarosh.tais.ui.core.validators.NotBlankValidator;
import ua.org.tees.yarosh.tais.ui.views.common.api.EditProfileTais;

import static com.vaadin.event.ShortcutAction.KeyCode.ENTER;
import static ua.org.tees.yarosh.tais.core.common.dto.Roles.*;
import static ua.org.tees.yarosh.tais.ui.RoleTranslator.translate;
import static ua.org.tees.yarosh.tais.ui.core.DataBinds.UriFragments.EDIT_PROFILE;
import static ua.org.tees.yarosh.tais.ui.core.UIFactoryAccessor.getCurrent;
import static ua.org.tees.yarosh.tais.ui.core.VaadinUtils.isValid;
import static ua.org.tees.yarosh.tais.ui.views.common.api.EditProfileTais.EditProfilePresenter;

/**
 * @author Timur Yarosh
 *         Date: 06.04.14
 *         Time: 13:56
 */
@TaisView("Редактирование профиля")
@PresentedBy(EditProfilePresenter.class)
@Qualifier(EDIT_PROFILE)
@PermitRoles({ADMIN, TEACHER, STUDENT})
public class EditProfile extends DashboardView implements EditProfileTais {

    private PasswordField password = new PasswordField();
    private PasswordField repeatPassword = new PasswordField();
    private TextField surname = new TextField();
    private TextField name = new TextField();
    private TextField patronymic = new TextField();
    private TextField email = new TextField();
    private ComboBox studentGroup = new ComboBox();
    private ComboBox role = new ComboBox();
    private DashPanel editPanel;
    private boolean controlsAdded;  // to avoid cyclic bean creation and spring crash
    private boolean formsAdded;     // additional controls creation moved from constructor
    private HorizontalLayout controls = createControls();

    public EditProfile() {
        super();
        setUpValidators();
        editPanel = addDashPanel(null, null,
                createSingleFormLayout(new Label("Фамилия"), surname),
                createSingleFormLayout(new Label("Имя"), name),
                createSingleFormLayout(new Label("Отчество"), patronymic),
                createSingleFormLayout(new Label("Email"), email),
                createSingleFormLayout(new Label("Новый пароль"), password),
                createSingleFormLayout(new Label("Повтор пароля"), repeatPassword));

        editPanel.setSizeUndefined();
        editPanel.setWidth(50, Unit.PERCENTAGE);
        dash.setComponentAlignment(editPanel, Alignment.MIDDLE_CENTER);
    }

    @Override
    public void update() {
        EditProfilePresenter presenter = getCurrent().getRelativePresenter(this, EditProfilePresenter.class);
        if (presenter.isAdminRightsAllowed() && !formsAdded) {
            editPanel.addComponents(
                    createSingleFormLayout(new Label("Группа"), studentGroup),
                    createSingleFormLayout(new Label("Роль"), role));
            // some magic
            editPanel.removeComponent(controls);
            editPanel.addComponent(controls);
            // controls moved under new forms :)
            formsAdded = true;
        }
        if (!controlsAdded) {
            editPanel.addComponent(controls);
            controlsAdded = true;
        }

        Registrant registrant = presenter.getRegistrant();
        if (registrant != null) {
            surname.setValue(registrant.getSurname());
            name.setValue(registrant.getName());
            patronymic.setValue(registrant.getPatronymic());
            email.setValue(registrant.getEmail());

            studentGroup.removeAllItems();
            presenter.groups().forEach(g -> {
                studentGroup.addItem(g);
                if (g.equals(registrant.getGroup())) studentGroup.setValue(g);
            });

            role.removeAllItems();
            presenter.roles().forEach(role::addItem);
            role.setValue(translate(registrant.getRole()));
        }
    }

    private HorizontalLayout createControls() {
        HorizontalLayout controlsLayout = new HorizontalLayout() {
            {
                setWidth(100, Unit.PERCENTAGE);
                setSpacing(true);
            }
        };

        Button apply = new Button("Сохранить изменения");
        apply.setClickShortcut(ENTER);
        apply.addClickListener(event -> {
            if (isValid(surname, name, patronymic, email)) {
                EditProfilePresenter presenter = getCurrent().getRelativePresenter(this, EditProfilePresenter.class);
                Registrant registrant = presenter.getRegistrant();
                registrant.setSurname(surname.getValue());
                registrant.setName(name.getValue());
                registrant.setPatronymic(patronymic.getValue());
                registrant.setEmail(email.getValue());

                if (!password.getValue().isEmpty() && isValid(password, repeatPassword)) {
                    registrant.setPassword(DigestUtils.sha256Hex(password.getValue()));
                }

                if (isValid(studentGroup)) {
                    registrant.setGroup((StudentGroup) studentGroup.getValue());
                } else {
                    Notification.show("Группа заполнена неправильно");
                }

                if (isValid(role)) {
                    registrant.setRole((String) role.getValue());
                } else {
                    Notification.show("Роль заполнена неправильно");
                }

                presenter.updateRegistrant(registrant);
            } else {
                Notification.show("Не все поля корректно заполнены");
            }
        });

        controlsLayout.addComponent(apply);
        controlsLayout.setComponentAlignment(apply, Alignment.BOTTOM_RIGHT);
        return controlsLayout;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        update();
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

    private void setUpValidators() {
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
//        studentGroup.addValidator(new NotBlankValidator("Некорректно заполнено поле"));
//        studentGroup.setValidationVisible(false);
        role.addValidator(new NotBlankValidator("Некорректно заполнено поле"));
        role.setValidationVisible(false);
    }
}
