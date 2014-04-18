package ua.org.tees.yarosh.tais.auth.api;

import ua.org.tees.yarosh.tais.auth.events.LoginEvent;
import ua.org.tees.yarosh.tais.core.common.api.Listener;

public interface LoginListener extends Listener {

    void onLogin(LoginEvent event);
}
