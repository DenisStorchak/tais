package ua.org.tees.yarosh.tais.attendance.schedule.api;

import ua.org.tees.yarosh.tais.attendance.schedule.models.Lesson;
import ua.org.tees.yarosh.tais.core.common.models.StudentGroup;

import java.util.Date;
import java.util.List;

public interface LessonsScheduleService {
    /**
     * Save new or replace old schedule for one student group
     * Accepts List with lessons for only one student group.
     * If list contains multiple groups lessons only first group schedule will be saved or replaced
     *
     * @param lessonList Full semester schedule
     */
    void saveOrReplaceSchedule(List<Lesson> lessonList);

    List<Lesson> findSchedule(Date periodFrom, Date periodTo, StudentGroup studentGroup);
}
