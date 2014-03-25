package ua.org.tees.yarosh.tais.ui.core.mvp;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Timur Yarosh
 */
@Retention(value = RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface PresenterClass {
    Class<? extends Presenter> value();
}
