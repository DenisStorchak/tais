package ua.org.tees.yarosh.tais.ui.views.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import ua.org.tees.yarosh.tais.attendance.schedule.models.Lesson;
import ua.org.tees.yarosh.tais.ui.core.HelpManager;
import ua.org.tees.yarosh.tais.ui.core.mvp.AbstractPresenter;
import ua.org.tees.yarosh.tais.ui.core.mvp.PresenterBasedView;

import static ua.org.tees.yarosh.tais.ui.core.text.UriFragments.Admin.CREATE_SCHEDULE;
import static ua.org.tees.yarosh.tais.ui.views.admin.CreateScheduleTaisView.CreateSchedulePresenter;

@Service
@Scope("prototype")
public class CreateScheduleListener extends AbstractPresenter implements CreateSchedulePresenter {

    @Autowired
    public CreateScheduleListener(@Qualifier(CREATE_SCHEDULE) PresenterBasedView view, HelpManager helpManager) {
        super(view, helpManager);
    }

    @Override
    public void addLesson(Lesson lesson) {

    }

    @Override
    public void removeLesson(Lesson lesson) {

    }
}
