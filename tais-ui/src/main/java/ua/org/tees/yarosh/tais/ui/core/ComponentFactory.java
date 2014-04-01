package ua.org.tees.yarosh.tais.ui.core;

import ua.org.tees.yarosh.tais.ui.core.mvp.HelpManagerFactory;
import ua.org.tees.yarosh.tais.ui.core.mvp.PresenterFactory;
import ua.org.tees.yarosh.tais.ui.core.mvp.ViewFactory;
import ua.org.tees.yarosh.tais.ui.core.mvp.WindowFactory;

public interface ComponentFactory extends ViewFactory, WindowFactory, PresenterFactory, HelpManagerFactory {
}
