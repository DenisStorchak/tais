package ua.org.tees.yarosh.tais.ui;

import ua.org.tees.yarosh.tais.attendance.schedule.LessonType;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Timur Yarosh
 *         Date: 29.03.14
 *         Time: 19:47
 */
public class LessonTypeTranslator {
    private static final Map<String, String> BUNDLE = new HashMap<String, String>() {
        {
            put(LessonType.LECTURE.toString(), "Лекция");
            put(LessonType.PRACTICE.toString(), "Практика");
        }
    };

    public static String translate(String lessonType) {
        if (BUNDLE.containsKey(lessonType)) {
            return BUNDLE.get(lessonType);
        } else if (BUNDLE.containsValue(lessonType)) {
            return BUNDLE.entrySet().stream().filter(r -> r.getValue().equals(lessonType)).findFirst().get().getValue();
        }
        return "";
    }
}
