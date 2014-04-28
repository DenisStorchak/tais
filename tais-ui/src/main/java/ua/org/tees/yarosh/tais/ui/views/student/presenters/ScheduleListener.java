package ua.org.tees.yarosh.tais.ui.views.student.presenters;

import com.vaadin.data.Container;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import ua.org.tees.yarosh.tais.core.common.models.Registrant;
import ua.org.tees.yarosh.tais.core.common.models.StudentGroup;
import ua.org.tees.yarosh.tais.core.user.mgmt.api.service.RegistrantService;
import ua.org.tees.yarosh.tais.schedule.api.ScheduleService;
import ua.org.tees.yarosh.tais.schedule.models.Lesson;
import ua.org.tees.yarosh.tais.ui.components.windows.CreateScheduleWindow;
import ua.org.tees.yarosh.tais.ui.core.UIFactory;
import ua.org.tees.yarosh.tais.ui.core.api.Updateable;
import ua.org.tees.yarosh.tais.ui.core.mvp.AbstractPresenter;
import ua.org.tees.yarosh.tais.ui.core.mvp.TaisPresenter;
import ua.org.tees.yarosh.tais.ui.views.student.api.ScheduleTaisView;

import java.util.*;

import static java.util.Arrays.asList;
import static ua.org.tees.yarosh.tais.ui.core.api.DataBinds.UriFragments.Admin.MANAGED_SCHEDULE;

@TaisPresenter
public class ScheduleListener extends AbstractPresenter implements ScheduleTaisView.SchedulePresenter {

    private RegistrantService registrantService;
    private ScheduleService scheduleService;

    @Autowired
    public void setRegistrantService(RegistrantService registrantService) {
        this.registrantService = registrantService;
    }

    @Autowired
    public void setScheduleService(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    public List<StudentGroup> getGroups() {
        return registrantService.findAllStudentGroups();
    }

    public List<Registrant> getRegistrants() {
        return registrantService.findAllRegistrants();
    }

    @Autowired
    public ScheduleListener(@Qualifier(MANAGED_SCHEDULE) Updateable view) {
        super(view);
    }

    @Override
    public Map<Date, List<Lesson>> getSchedule(Object owner, Date periodFrom, Date periodTo) {
        if (owner instanceof StudentGroup) {
            StudentGroup studentGroup = registrantService.findStudentGroup(((StudentGroup) owner).getId());
            List<Lesson> schedule = scheduleService.findSchedule(periodFrom, periodTo, studentGroup);
            return createLessonsDateMap(schedule);
        } else if (owner instanceof Registrant) {
            Registrant registration = registrantService.getRegistration(((Registrant) owner).getLogin());
            List<Lesson> schedule = scheduleService.findSchedule(periodFrom, periodTo, registration.getGroup());
            return createLessonsDateMap(schedule);
        }
        throw new IllegalArgumentException("Owner must be instance of StudentGroup or Registrant");
    }

    @Override
    public CreateScheduleWindow getCreateScheduleWindow(Object studentGroup, Container lessons, Date lastDate) {
        CreateScheduleWindow window = UIFactory.getCurrent().getWindow(CreateScheduleWindow.class);
        window.setListener(this);
        window.setScheduleContainer(lessons);
        window.setStudentGroup((StudentGroup) studentGroup);
        window.setLastDate(lastDate);
        window.setContent();
        return window;
    }

    @Override
    public void saveOrReplaceSchedule(List<Lesson> lessons) {
        scheduleService.saveOrReplaceSchedule(lessons);
    }

    private Map<Date, List<Lesson>> createLessonsDateMap(List<Lesson> schedule) {
        Map<Date, List<Lesson>> lessonsDateMap = new HashMap<>();
        for (Lesson lesson : schedule) {
            if (lessonsDateMap.containsKey(lesson.getDate())) {
                lessonsDateMap.get(lesson.getDate()).add(lesson);
            } else {
                lessonsDateMap.put(lesson.getDate(), new ArrayList<>(asList(lesson)));
            }
        }
        return lessonsDateMap;
    }
}
