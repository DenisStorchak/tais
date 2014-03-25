package ua.org.tees.yarosh.tais.ui.core;

/**
 * @author Timur Yarosh
 *         Date: 21.03.14
 *         Time: 20:23
 */
public abstract class UriFragments {

    public static final String AUTH = "auth";

    public abstract class Admin {
        public static final String PREFIX = "admin/";
        public static final String USER_REGISTRATION = PREFIX + "registration";
        public static final String USER_MANAGEMENT = PREFIX + "usermgmt";
        public static final String MANAGED_SCHEDULE = PREFIX + "schedule";
        public static final String CREATE_SCHEDULE = PREFIX + "schedule/create";
    }

    public abstract class Teacher {
        public static final String PREFIX = "teacher/";
        public static final String TEACHER_DASHBOARD = PREFIX + "dashboard";
    }
}
