package ua.org.tees.yarosh.tais.schedule.api;

import ua.org.tees.yarosh.tais.core.common.models.StudentGroup;
import ua.org.tees.yarosh.tais.schedule.LessonsNotFoundException;
import ua.org.tees.yarosh.tais.schedule.models.Lesson;

import java.util.Date;
import java.util.List;

public interface ScheduleService {
    /**
     * Save new or replace old schedule for one student group
     * Accepts List with lessons for only one student group.
     * If list contains multiple groups lessons only first group schedule will be saved or replaced
     *
     * @param lessonList Full semester schedule
     */
    void saveOrReplaceSchedule(List<Lesson> lessonList);

    List<Lesson> findSchedule(Date periodFrom, Date periodTo, StudentGroup studentGroup);

    Lesson findCurrentOrNextLesson(String studentGroup) throws LessonsNotFoundException;
}
