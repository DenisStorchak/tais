package ua.org.tees.yarosh.tais.ui.views.student.api;

import com.vaadin.navigator.View;
import ua.org.tees.yarosh.tais.core.common.models.Registrant;
import ua.org.tees.yarosh.tais.core.common.models.StudentGroup;
import ua.org.tees.yarosh.tais.schedule.models.Lesson;
import ua.org.tees.yarosh.tais.ui.core.api.Updateable;
import ua.org.tees.yarosh.tais.ui.core.mvp.Presenter;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface ScheduleTaisView extends View, Updateable {

    interface SchedulePresenter extends Presenter {
        Map<? extends Date, ? extends List<Lesson>> getSchedule(Object owner, Date periodFrom, Date periodTo);

        List<StudentGroup> getGroups();

        List<Registrant> getRegistrants();
    }
}
