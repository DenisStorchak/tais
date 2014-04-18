package ua.org.tees.yarosh.tais.auth.events;

import ua.org.tees.yarosh.tais.auth.UserDetails;

public class LoginEvent {

    private UserDetails userDetails;

    public UserDetails getUserDetails() {
        return userDetails;
    }

    public LoginEvent(UserDetails userDetails) {
        this.userDetails = userDetails;
    }
}
