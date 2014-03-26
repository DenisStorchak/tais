package ua.org.tees.yarosh.tais.ui.core;

import com.vaadin.navigator.View;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Timur Yarosh
 *         Date: 22.03.14
 *         Time: 17:05
 */
public class ViewResolver {

    private static final Map<Class<? extends View>, String> resolverMap = new HashMap<>();

    public static String resolveView(Class<? extends View> clazz) {
        if (resolverMap.containsKey(clazz)) {
            return resolverMap.get(clazz);
        }
        throw new IllegalArgumentException("View not found");
    }

    public static boolean viewRegistered(String name) {
        return resolverMap.containsValue(name);
    }

    public void register(Class<? extends View> clazz, String name) {
        resolverMap.put(clazz, name);
    }
}
