package ua.org.tees.yarosh.tais.schedule;

import org.joda.time.LocalDateTime;
import ua.org.tees.yarosh.tais.schedule.models.Lesson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ScheduleUtils {
    public static List<Lesson> copyToPeriod(Lesson lesson, int daysStep) {
        LocalDateTime lessonFirstDateTime = LocalDateTime.fromDateFields(lesson.getDate());
        ArrayList<Lesson> lessons = new ArrayList<>(Arrays.asList(lesson));
        LocalDateTime nextLessonDate = null;
        for (int i = 0; i < daysStep; i++) {
            if (nextLessonDate == null) {
                nextLessonDate = lessonFirstDateTime.plusDays(1);
            } else {
                nextLessonDate = nextLessonDate.plusDays(1);
            }
            Lesson nextLesson = new Lesson(lesson);
            nextLesson.setDate(nextLessonDate.toDate());
            lessons.add(nextLesson);
            lessons.add(lesson);
        }
        if (lessons.isEmpty()) lessons.add(lesson);
        return lessons;
    }
}
