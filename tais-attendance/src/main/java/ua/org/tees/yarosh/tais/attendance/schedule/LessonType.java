package ua.org.tees.yarosh.tais.attendance.schedule;

public enum LessonType {
    PRACTICE {
        @Override
        public String toString() {
            return "Пратика";
        }
    }, LECTURE {
        @Override
        public String toString() {
            return "Лекция";
        }
    }
}
