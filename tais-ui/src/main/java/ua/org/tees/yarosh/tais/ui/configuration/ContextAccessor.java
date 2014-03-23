package ua.org.tees.yarosh.tais.ui.configuration;

import org.springframework.context.ApplicationContext;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Timur Yarosh
 *         Date: 23.03.14
 *         Time: 2:24
 */
public class ContextAccessor {
    private static final Map<Class<?>, ApplicationContext> contextPool = new HashMap<>();

    public static void addContext(Class<?> clazz, ApplicationContext context) {
        contextPool.put(clazz, context);
    }

    public static ApplicationContext getContext(Class<?> clazz) {
        return contextPool.get(clazz);
    }

    public static void deleteContext(Class<?> clazz) {
        contextPool.remove(clazz);
    }
}
