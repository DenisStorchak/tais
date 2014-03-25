package ua.org.tees.yarosh.tais.ui.views.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import ua.org.tees.yarosh.tais.ui.core.HelpManager;
import ua.org.tees.yarosh.tais.ui.core.components.PresenterBasedView;
import ua.org.tees.yarosh.tais.ui.core.mvp.AbstractPresenter;

import static ua.org.tees.yarosh.tais.ui.core.UriFragments.Admin.CREATE_SCHEDULE;
import static ua.org.tees.yarosh.tais.ui.views.admin.CreateScheduleTaisView.CreateSchedulePresenter;

@Service
@Scope("prototype")
public class CreateScheduleListener extends AbstractPresenter implements CreateSchedulePresenter {

    @Autowired
    public CreateScheduleListener(@Qualifier(CREATE_SCHEDULE) PresenterBasedView view, HelpManager helpManager) {
        super(view, helpManager);
    }
}
