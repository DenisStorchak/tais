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
    private static final Map<String, ApplicationContext> contextPool = new HashMap<>();

    public static void addContext(String contextName, ApplicationContext context) {
        contextPool.put(contextName, context);
    }

    public static ApplicationContext getContext(String contextName) {
        return contextPool.get(contextName);
    }

    public static void deleteContext(String contextName) {
        contextPool.remove(contextName);
    }
}
