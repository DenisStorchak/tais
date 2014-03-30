package ua.org.tees.yarosh.tais.ui.views.admin.presenters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import ua.org.tees.yarosh.tais.core.common.models.Registrant;
import ua.org.tees.yarosh.tais.core.common.models.StudentGroup;
import ua.org.tees.yarosh.tais.core.user.mgmt.api.service.RegistrantService;
import ua.org.tees.yarosh.tais.schedule.api.ScheduleService;
import ua.org.tees.yarosh.tais.schedule.models.Lesson;
import ua.org.tees.yarosh.tais.ui.core.HelpManager;
import ua.org.tees.yarosh.tais.ui.core.mvp.AbstractPresenter;
import ua.org.tees.yarosh.tais.ui.core.mvp.PresenterBasedView;
import ua.org.tees.yarosh.tais.ui.views.admin.api.ScheduleTaisView;

import java.util.*;

import static java.util.stream.Collectors.toList;
import static ua.org.tees.yarosh.tais.ui.core.text.UriFragments.Admin.MANAGED_SCHEDULE;

@Service
@Scope("prototype")
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

    @Override
    public void update() {
        ScheduleTaisView view = getView(ScheduleTaisView.class);
        view.setGroups(getGroups());
        view.setRegistrants(getRegistrants());
    }

    private List<String> getGroups() {
        return registrantService.listStudentGroups().stream().map(StudentGroup::getId).collect(toList());
    }

    private List<String> getRegistrants() {
        return registrantService.findAllRegistrants().stream().map(Registrant::toString).collect(toList());
    }

    @Autowired
    public ScheduleListener(@Qualifier(MANAGED_SCHEDULE) PresenterBasedView view, HelpManager helpManager) {
        super(view, helpManager);
    }

    @Override
    public Map<Date, List<Lesson>> getSchedule(String ownerId, Date periodFrom, Date periodTo) {
        if (registrantService.isStudentGroupExists(ownerId)) {
            return createLessonsDateMap(
                    scheduleService
                            .findSchedule(periodFrom, periodTo, registrantService.findStudentGroup(ownerId))
            );
        } else if (registrantService.loginExists(ownerId)) {
            return createLessonsDateMap(
                    scheduleService
                            .findSchedule(periodFrom, periodTo, registrantService.getRegistration(ownerId).getGroup())
            );
        }
        return null;
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
                lessonsDateMap.put(lesson.getDate(), Arrays.asList(lesson));
            }
        }
        return lessonsDateMap;
    }
}
