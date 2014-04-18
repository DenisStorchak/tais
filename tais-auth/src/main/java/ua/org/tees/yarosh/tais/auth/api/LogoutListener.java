package ua.org.tees.yarosh.tais.auth.api;

import ua.org.tees.yarosh.tais.auth.events.LogoutEvent;
import ua.org.tees.yarosh.tais.core.common.api.Listener;

public interface LogoutListener extends Listener {
    void onLogout(LogoutEvent event);
}
