package ua.org.tees.yarosh.tais.ui.views.admin;

import ua.org.tees.yarosh.tais.attendance.schedule.models.Lesson;
import ua.org.tees.yarosh.tais.ui.core.mvp.Presenter;
import ua.org.tees.yarosh.tais.ui.core.mvp.PresenterBasedView;

import java.util.List;

public interface CreateScheduleTaisView extends PresenterBasedView<CreateScheduleTaisView.CreateSchedulePresenter> {

    void setGroups(List<String> groups);

    interface CreateSchedulePresenter extends Presenter {
        void addLesson(Lesson lesson);

        void removeLesson(Lesson lesson);

    }
}
