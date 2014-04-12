package ua.org.tees.yarosh.tais.core.common;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author Timur Yarosh
 *         Date: 12.04.14
 *         Time: 23:08
 */
@Component
public class EventHandlerReceptionist implements BeanPostProcessor {

    public static final Logger log = LoggerFactory.getLogger(EventHandlerReceptionist.class);
    private EventBus eventBus;

    @Autowired
    public void setEventBus(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    public Object postProcessBeforeInitialization(Object o, String s) throws BeansException {
        return o;
    }

    @Override
    public Object postProcessAfterInitialization(Object o, String s) throws BeansException {
        for (Method method : o.getClass().getMethods()) {
            if (method.isAnnotationPresent(Subscribe.class)) {
                eventBus.register(o);
                log.debug("[{}] handler registered", method.getParameterTypes()[0].getName());
            }
        }
        return null;
    }
}
