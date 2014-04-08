package ua.org.tees.yarosh.tais.ui.core.api;

import com.vaadin.ui.Window;

public interface WindowFactory {
    <W extends Window> W getWindow(Class<W> windowType);
}
