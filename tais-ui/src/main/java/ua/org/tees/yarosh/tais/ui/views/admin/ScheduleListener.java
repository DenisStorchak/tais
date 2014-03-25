package ua.org.tees.yarosh.tais.ui.views.admin;

import ua.org.tees.yarosh.tais.ui.core.HelpManager;
import ua.org.tees.yarosh.tais.ui.core.components.PresenterBasedView;
import ua.org.tees.yarosh.tais.ui.core.mvp.AbstractPresenter;

public class ScheduleListener extends AbstractPresenter implements ScheduleTaisView.SchedulePresenter {
    public ScheduleListener(PresenterBasedView view, HelpManager helpManager) {
        super(view, helpManager);
    }
}
