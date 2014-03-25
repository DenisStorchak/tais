package ua.org.tees.yarosh.tais.ui.core.mvp;

import com.vaadin.navigator.View;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Timur Yarosh
 *         Date: 25.03.14
 *         Time: 21:54
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface PresentsView {
    Class<? extends View> value();
}
