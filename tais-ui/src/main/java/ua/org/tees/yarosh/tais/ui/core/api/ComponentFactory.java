package ua.org.tees.yarosh.tais.ui.core.api;

import ua.org.tees.yarosh.tais.ui.core.mvp.PresenterFactory;
import ua.org.tees.yarosh.tais.ui.core.mvp.ViewFactory;

public interface ComponentFactory extends ViewFactory, WindowFactory,
        PresenterFactory, HelpManagerFactory, SidebarManagerFactory {
}
