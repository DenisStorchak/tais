package ua.org.tees.yarosh.tais.ui.views.common.api;

import com.vaadin.navigator.View;
import ua.org.tees.yarosh.tais.core.common.models.Registrant;
import ua.org.tees.yarosh.tais.core.common.models.StudentGroup;
import ua.org.tees.yarosh.tais.ui.core.api.UpdatableView;
import ua.org.tees.yarosh.tais.ui.core.mvp.Presenter;

import java.util.List;

/**
 * @author Timur Yarosh
 *         Date: 06.04.14
 *         Time: 12:42
 */
public interface EditProfileTaisView extends View, UpdatableView {

    interface EditProfilePresenter extends Presenter {
        void setRegistrantId(String login);

        Registrant getRegistrant();

        void allowAdminRights(boolean allow);

        boolean isAdminRightsAllowed();

        void updateRegistrant(Registrant registrant);

        List<StudentGroup> groups();

        List<String> roles();
    }
}
