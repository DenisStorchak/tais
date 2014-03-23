package ua.org.tees.yarosh.tais.ui.core;

/**
 * @author Timur Yarosh
 *         Date: 21.03.14
 *         Time: 20:23
 */
public abstract class UriFragments {

    public static final String AUTH = "auth";

    public abstract class Admin {
        private static final String PREFIX = "admin/";
        public static final String USER_REGISTRATION = PREFIX + "registration/user";
    }

    public abstract class Teacher {
        private static final String PREFIX = "teacher/";
        public static final String TEACHER_DASHBOARD = PREFIX + "dashboard";
    }
}
