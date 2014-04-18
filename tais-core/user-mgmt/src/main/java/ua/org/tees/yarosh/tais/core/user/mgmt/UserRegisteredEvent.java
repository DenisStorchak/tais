package ua.org.tees.yarosh.tais.core.user.mgmt;

import ua.org.tees.yarosh.tais.core.common.models.Registrant;

public class UserRegisteredEvent {
    private Registrant registrant;

    public Registrant getRegistrant() {
        return registrant;
    }

    public UserRegisteredEvent(Registrant registrant) {
        this.registrant = registrant;
    }
}
