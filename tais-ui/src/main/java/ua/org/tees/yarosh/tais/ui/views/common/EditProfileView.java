package ua.org.tees.yarosh.tais.ui.views.common;

import com.vaadin.navigator.ViewChangeListener;
import org.springframework.beans.factory.annotation.Qualifier;
import ua.org.tees.yarosh.tais.ui.core.mvp.DashboardLayout;
import ua.org.tees.yarosh.tais.ui.core.mvp.TaisView;
import ua.org.tees.yarosh.tais.ui.views.common.api.EditProfileTaisView;

import static ua.org.tees.yarosh.tais.ui.core.DataBinds.UriFragments.ME;

/**
 * @author Timur Yarosh
 *         Date: 06.04.14
 *         Time: 13:56
 */
@TaisView
@Qualifier(ME)
public class EditProfileView extends DashboardLayout implements EditProfileTaisView {

    private static final String CAPTION = "Редакторивание профиля";

    protected EditProfileView() {
        super(CAPTION);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        update();
    }
}
