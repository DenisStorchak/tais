package ua.org.tees.yarosh.tais.ui.views.common.presenters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import ua.org.tees.yarosh.tais.core.common.exceptions.RegistrantNotFoundException;
import ua.org.tees.yarosh.tais.core.common.models.Registrant;
import ua.org.tees.yarosh.tais.core.user.mgmt.api.service.RegistrantService;
import ua.org.tees.yarosh.tais.ui.core.mvp.AbstractPresenter;
import ua.org.tees.yarosh.tais.ui.core.mvp.TaisPresenter;
import ua.org.tees.yarosh.tais.ui.core.mvp.UpdatableView;

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
    private String registrantId;

    @Autowired
    public void setRegistrantService(RegistrantService registrantService) {
        this.registrantService = registrantService;
    }

    public EditProfileListener(@Qualifier(EDIT_PROFILE) UpdatableView view) {
        super(view);
    }

    @Override
    public void setRegistrantId(String login) {
        this.registrantId = login;
    }

    @Override
    public Registrant getRegistrant() {
        return registrantService.getRegistration(registrantId);
    }

    @Override
    public void updateRegistrant(Registrant registrant) {
        try {
            registrantService.updateRegistration(registrant);
        } catch (RegistrantNotFoundException e) { /* NOP */ }
    }
}
