package ua.org.tees.yarosh.tais.attendance.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.org.tees.yarosh.tais.attendance.schedule.api.LessonsRepository;
import ua.org.tees.yarosh.tais.attendance.schedule.api.LessonsScheduleService;
import ua.org.tees.yarosh.tais.attendance.schedule.models.Lesson;
import ua.org.tees.yarosh.tais.core.common.models.StudentGroup;

import java.util.Date;
import java.util.List;

@Service
public class LessonScheduler implements LessonsScheduleService {

    private LessonsRepository lessonsRepository;

    @Autowired
    public void setLessonsRepository(LessonsRepository lessonsRepository) {
        this.lessonsRepository = lessonsRepository;
    }

    @Override
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
    public List<Lesson> findSchedule(Date periodFrom, Date periodTo, StudentGroup studentGroup) {
        return lessonsRepository.findLessonsByPeriod(periodFrom, periodTo, studentGroup);
    }
}
