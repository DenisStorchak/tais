package ua.org.tees.yarosh.tais.ui.core.api;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author Timur Yarosh
 *         Date: 27.04.14
 *         Time: 14:54
 */
@Target(TYPE)
@Retention(RUNTIME)
public @interface PresenterUnnecessary {
}
