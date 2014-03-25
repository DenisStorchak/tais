package ua.org.tees.yarosh.tais.ui.views.admin;

import ua.org.tees.yarosh.tais.ui.core.components.PresenterBasedView;
import ua.org.tees.yarosh.tais.ui.core.mvp.Presenter;

import java.util.List;

public interface CreateScheduleTaisView extends PresenterBasedView<CreateScheduleTaisView.CreateSchedulePresenter> {

    void setGroups(List<String> groups);

    interface CreateSchedulePresenter extends Presenter {

    }
}
