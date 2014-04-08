package ua.org.tees.yarosh.tais.ui.core.api;

import org.springframework.stereotype.Service;

/**
 * @author Timur Yarosh
 *         Date: 23.03.14
 *         Time: 2:24
 */
@Service
public interface UIContext {
    public <T> T getBean(Class<T> clazz);
}
