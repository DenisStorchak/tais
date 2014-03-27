package ua.org.tees.yarosh.tais.ui;

import ua.org.tees.yarosh.tais.core.common.dto.Role;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Timur Yarosh
 *         Date: 27.03.14
 *         Time: 21:04
 */
public class RoleTranslator {
    private static final Map<String, String> BUNDLE = new HashMap<String, String>() {
        {
            put(Role.TEACHER, "Преподаватель");
            put(Role.ADMIN, "Администратор");
            put(Role.STUDENT, "Студент");
        }
    };

    public static String translate(String role) {
        if (BUNDLE.containsKey(role)) {
            return BUNDLE.get(role);
        } else if (BUNDLE.containsValue(role)) {
            return BUNDLE.entrySet().stream().filter(r -> r.getValue().equals(role)).findFirst().get().getValue();
        }
        return "";
    }
}
