package ua.org.tees.yarosh.tais.ui.views.common.presenters;

import com.vaadin.ui.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import ua.org.tees.yarosh.tais.auth.AuthManager;
import ua.org.tees.yarosh.tais.core.common.exceptions.RegistrantNotFoundException;
import ua.org.tees.yarosh.tais.core.common.models.Registrant;
import ua.org.tees.yarosh.tais.core.common.models.StudentGroup;
import ua.org.tees.yarosh.tais.core.user.mgmt.api.service.RegistrantService;
import ua.org.tees.yarosh.tais.ui.core.mvp.AbstractPresenter;
import ua.org.tees.yarosh.tais.ui.core.mvp.TaisPresenter;
import ua.org.tees.yarosh.tais.ui.core.mvp.UpdatableView;

import java.util.List;

import static java.util.Arrays.asList;
import static ua.org.tees.yarosh.tais.core.common.dto.Roles.*;
import static ua.org.tees.yarosh.tais.ui.RoleTranslator.translate;
import static ua.org.tees.yarosh.tais.ui.core.DataBinds.UriFragments.EDIT_PROFILE;
import static ua.org.tees.yarosh.tais.ui.views.common.api.EditProfileTaisView.EditProfilePresenter;

/**
 * @author Timur Yarosh
 *         Date: 06.04.14
 *         Time: 13:40
 */
@TaisPresenter
public class EditProfileListener extends AbstractPresenter implements EditProfilePresenter {

    private RegistrantService registrantService;
    private boolean adminRights;
    private String registrantId;

    @Autowired
    public void setRegistrantService(RegistrantService registrantService) {
        this.registrantService = registrantService;
    }

    @Autowired
    public EditProfileListener(@Qualifier(EDIT_PROFILE) UpdatableView view) {
        super(view);
    }

    @Override
    public void setRegistrantId(String login) {
        this.registrantId = login;
    }

    @Override
    public Registrant getRegistrant() {
        if (registrantId != null) return registrantService.getRegistration(registrantId);
        else return null;
    }

    @Override
    public void allowAdminRights(boolean allow) {
        this.adminRights = allow;
    }

    @Override
    public boolean isAdminRightsAllowed() {
        return adminRights;
    }

    @Override
    public void updateRegistrant(Registrant registrant) {
        try {
            registrant.setRole(translate(registrant.getRole()));
            if (registrantService.updateRegistration(registrant) != null) {
                Notification.show("Регистрационные данные успешно обновлены");
                if (adminRights) AuthManager.logout(registrant.getLogin());
            } else {
                Notification.show("Что-то пошло не так...");
            }
        } catch (RegistrantNotFoundException e) {
            Notification.show("Что-то пошло не так...");
        }
    }

    @Override
    public List<StudentGroup> groups() {
        return registrantService.listStudentGroups();
    }

    @Override
    public List<String> roles() {
        return asList(translate(ADMIN), translate(TEACHER), translate(STUDENT));
    }
}
