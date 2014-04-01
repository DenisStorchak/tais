package ua.org.tees.yarosh.tais.ui.views.admin.api;

import com.vaadin.navigator.View;
import ua.org.tees.yarosh.tais.schedule.models.Lesson;
import ua.org.tees.yarosh.tais.ui.core.mvp.Presenter;
import ua.org.tees.yarosh.tais.ui.core.mvp.UpdatableView;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface ScheduleTaisView extends View, UpdatableView {

    public void setRegistrants(List<String> registrants);

    public void setGroups(List<String> groups);

    interface SchedulePresenter extends Presenter {
        Map<? extends Date, ? extends List<Lesson>> getSchedule(String ownerId, Date periodFrom, Date periodTo);

        void saveOrReplaceSchedule(List<Lesson> lessons);
    }
}
