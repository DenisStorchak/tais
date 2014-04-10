package ua.org.tees.yarosh.tais.ui.core;

public abstract class DataBinds {
    public static abstract class ErrorMessages {
        public static final String EMPTY_VALUE = "Поле не должно быть пустым";
        public static final String FIELDS_NOT_EQUALS = "%s должны совпадать";
        public static final String VALUE_TOO_SHORT = "Минимальное количество символов должно быть не меньше %d";

        public static String getFormattedMessage(String message, Object... params) {
            return String.format(message, params);
        }
    }

    public static abstract class Messages {
        public static final String WELCOME_MESSAGE = "TEES Automatic Interaction System — автоматическая система взаимодействия " +
                "кафедры ТЭЭС.\nДоступ к системе возможен только зарегистрированным пользователям";
        public static final String CREATE_TASK_BUTTON_DESCRIPTION = "Создать новое задание";
        public static final String CREATE_TASK_WINDOW_TITLE = "Создать задание";
        public static final String QUESTIONS_SUITE_TITLE = "Тестирование";
        public static final String MANUAL_TASK = "Задание";
        public static final String WELCOME_LABEL = "Добро пожаловать";
        public static final String APPLICATION_TITLE = "TEES Dashboard";
        public static final String LOGIN_FIELD_CAPTION = "Имя пользователя";
        public static final String PASSWORD_FIELD_CAPTION = "Пароль";
        public static final String SIGN_IN_BUTTON_CAPTION = "Вход";
    }

    public static abstract class SessionKeys {
        public static final String PREVIOUS_VIEW = "previousView";
        public static final String REGISTRANT_ID = "registrantID";
        public static final String COMPONENT_FACTORY = "componentFactory";
    }

    public static abstract class UriFragments {

        public static final String AUTH = "auth";
        public static final String ACCESS_DENIED = "403";
        public static final String ME = "me";
        public static final String EDIT_PROFILE = "profile/edit";

        public abstract class Admin {

            public static final String PREFIX = "admin/";
            public static final String USER_REGISTRATION = PREFIX + "registration";
            public static final String USER_MANAGEMENT = PREFIX + "usermgmt";
            public static final String MANAGED_SCHEDULE = PREFIX + "schedule";
            public static final String CREATE_SCHEDULE = PREFIX + "schedule/create";
            public static final String SETTINGS = PREFIX + "settings";
        }

        public abstract class Teacher {

            public static final String PREFIX = "teacher/";

            public static final String TEACHER_DASHBOARD = PREFIX + "dashboard";
            public static final String CREATE_QUESTIONS_SUITE = PREFIX + "questions/create";
            public static final String ADD_QUESTION = PREFIX + "question/add";
            public static final String ENABLED_QUESTIONS = PREFIX + "questions";
            public static final String STUDENTS = PREFIX + "students";
            public static final String ADD_MANUAL = PREFIX + "manual/add";
        }
    }

    public static abstract class Qualifiers {
        public static final String UI_FACTORY = "uiFactory";
        public static final String VIEW_FACTORY = "viewFactory";
        public static final String WINDOW_FACTORY = "windowFactory";
        public static final String LISTENER_FACTORY = "listenerFactory";
        public static final String PRESENTER_FACTORY = "presenterFactory";
        public static final String HELP_MANAGER_FACTORY = "helpManagerFactory";
    }

    public static abstract class Cookies {
        public static final String AUTH = "taisAuth";
    }

    public static abstract class FS {
        public static final String DEFAULT_EXTENSION = "tais";
        public static final String HOME_DIR = System.getProperty("user.home");
        public static final String MANUAL_PAYLOAD_DIR = "payload";
    }
}
