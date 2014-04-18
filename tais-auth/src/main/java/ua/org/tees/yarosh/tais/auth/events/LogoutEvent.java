package ua.org.tees.yarosh.tais.auth.events;

import ua.org.tees.yarosh.tais.auth.UserDetails;

public class LogoutEvent {

    private UserDetails userDetails;

    public UserDetails getUserDetails() {
        return userDetails;
    }

    public LogoutEvent(UserDetails userDetails) {
        this.userDetails = userDetails;
    }
}
