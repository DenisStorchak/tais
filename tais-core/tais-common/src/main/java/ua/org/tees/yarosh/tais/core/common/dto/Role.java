package ua.org.tees.yarosh.tais.core.common.dto;

/**
 * @author Timur Yarosh
 *         Date: 09.03.14
 *         Time: 22:25
 */
public enum Role {
    STUDENT {
        @Override
        public String toString() {
            return "Студент";
        }
    }, TEACHER {
        @Override
        public String toString() {
            return "Преподаватель";
        }
    }, ADMIN {
        @Override
        public String toString() {
            return "Администратор";
        }
    }
}
