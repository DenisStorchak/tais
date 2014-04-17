package ua.org.tees.yarosh.tais.ui.core.api;

import ua.org.tees.yarosh.tais.core.common.api.Listener;
import ua.org.tees.yarosh.tais.ui.core.events.LoginEvent;

public interface LoginListener extends Listener {
    void onLogin(LoginEvent event);
}
