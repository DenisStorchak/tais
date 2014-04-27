package ua.org.tees.yarosh.tais.ui.core;

import com.vaadin.navigator.View;
import org.springframework.beans.factory.annotation.Qualifier;
import ua.org.tees.yarosh.tais.core.common.models.Registrant;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Timur Yarosh
 *         Date: 22.03.14
 *         Time: 17:05
 */
public class ViewResolver {

    private static final Map<Class<? extends View>, String> resolverMap = new HashMap<>();
    private static final Map<String, String> roleViews = new HashMap<>();

    public static String resolveView(Class<? extends View> clazz) {
        if (resolverMap.containsKey(clazz)) {
            return resolverMap.get(clazz);
        }
        throw new IllegalArgumentException("View not found");
    }

    public static Class<? extends View> resolveView(String name) {
        if (resolverMap.containsValue(name)) {
            return resolverMap.entrySet().stream().filter(e -> e.getValue().equals(name)).findFirst().get().getKey();
        }
        throw new IllegalArgumentException("View not found");
    }

    public static String resolveUnregistered(View view) {
        Qualifier qualifier = view.getClass().getAnnotation(Qualifier.class);
        if (qualifier != null) {
            return qualifier.value();
        }
        throw new IllegalArgumentException("Qualifier not found");
    }

    public static String resolveUnregistered(Class<? extends View> clazz) {
        Qualifier qualifier = clazz.getAnnotation(Qualifier.class);
        if (qualifier != null) {
            return qualifier.value();
        }
        throw new IllegalArgumentException("Qualifier not found");
    }

    public static boolean viewRegistered(String name) {
        return resolverMap.containsValue(name);
    }

    public static String resolveDefaultView(Registrant registrant) {
        return roleViews.get(registrant.getRole());
    }

    public static void bindDefaultView(Class<? extends View> clazz, String role) {
        roleViews.put(role, resolveUnregistered(clazz));
    }

    public void register(Class<? extends View> clazz, String name) {
        resolverMap.put(clazz, name);
    }
}
