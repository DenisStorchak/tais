package ua.org.tees.yarosh.tais.attendance.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ua.org.tees.yarosh.tais.attendance.api.TeacherService;
import ua.org.tees.yarosh.tais.attendance.schedule.api.LessonsRepository;
import ua.org.tees.yarosh.tais.attendance.schedule.api.ScheduleService;
import ua.org.tees.yarosh.tais.attendance.schedule.models.Lesson;
import ua.org.tees.yarosh.tais.core.common.models.Discipline;
import ua.org.tees.yarosh.tais.core.common.models.Registrant;
import ua.org.tees.yarosh.tais.core.common.models.StudentGroup;

import java.util.Date;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static ua.org.tees.yarosh.tais.attendance.configuration.CacheNames.SCHEDULE;

@Service
public class ScheduleTeacherService implements ScheduleService, TeacherService {

    private LessonsRepository lessonsRepository;

    @Autowired
    public void setLessonsRepository(LessonsRepository lessonsRepository) {
        this.lessonsRepository = lessonsRepository;
    }

    @Override
    @CacheEvict(SCHEDULE)
    public void saveOrReplaceSchedule(List<Lesson> lessonList) {
        if (!lessonList.isEmpty()) {
            StudentGroup scheduleOwner = lessonList.get(0).getStudentGroup();
            lessonsRepository.deleteSchedule(scheduleOwner);
            for (Lesson lesson : lessonList) {
                if (lesson.getStudentGroup().getId().equals(scheduleOwner.getId())) {
                    lessonsRepository.save(lesson);
                }
            }
        }
    }

    @Override
    @Cacheable(SCHEDULE)
    public List<Lesson> findSchedule(Date periodFrom, Date periodTo, StudentGroup studentGroup) {
        return lessonsRepository.findLessonsWithinPeriod(periodFrom, periodTo, studentGroup);
    }

    @Override
    @Cacheable(SCHEDULE)
    public List<Discipline> findDisciplinesByTeacher(Registrant teacher) {
        return lessonsRepository.findLessonsByTeacher(teacher).stream().map(Lesson::getDiscipline).collect(toList());
    }
}
