package ua.org.tees.yarosh.tais.schedule;

import org.joda.time.LocalDateTime;
import ua.org.tees.yarosh.tais.schedule.models.Lesson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class ScheduleUtils {
    public static List<Lesson> copyToPeriod(Lesson lesson, int daysStep) {
        LocalDateTime lessonFirstDateTime = LocalDateTime.fromDateFields(lesson.getDate());
        LocalDateTime lessonLastDateTime = lessonFirstDateTime.plusDays(daysStep);
        ArrayList<Lesson> lessons = new ArrayList<>(Arrays.asList(lesson));
        LocalDateTime nextLessonDate = lessonFirstDateTime.minusDays(1);
        while (beforeOrEquals(nextLessonDate.toDate(), lessonLastDateTime.toDate())) {
            nextLessonDate = lessonFirstDateTime.plusDays(1);
            Lesson nextLesson = new Lesson(lesson);
            nextLesson.setDate(nextLessonDate.toDate());
            lessons.add(nextLesson);
        }
        return lessons;
    }

    private static boolean beforeOrEquals(Date lessonFirstDateTime, Date lessonLastDateTime) {
        return !lessonFirstDateTime.equals(lessonLastDateTime) && lessonFirstDateTime.before(lessonLastDateTime);
    }
}
