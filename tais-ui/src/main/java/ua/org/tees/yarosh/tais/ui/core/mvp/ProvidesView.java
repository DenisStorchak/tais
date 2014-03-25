package ua.org.tees.yarosh.tais.ui.core.mvp;

import com.vaadin.navigator.View;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ProvidesView {
    Class<? extends View> value();
}
