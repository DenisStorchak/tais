package ua.org.tees.yarosh.tais.ui.core.events;

import ua.org.tees.yarosh.tais.core.common.models.Registrant;

public class LoginEvent {
    private Registrant registrant;

    public Registrant getRegistrant() {
        return registrant;
    }

    public LoginEvent(Registrant registrant) {
        this.registrant = registrant;
    }
}
