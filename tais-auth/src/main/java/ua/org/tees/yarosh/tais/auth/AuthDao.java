package ua.org.tees.yarosh.tais.auth;

public interface AuthDao {
    /**
     * Retrieve user details from somewhere
     *
     * @param login username
     * @return UserDetails instance if success or null if failure
     */
    UserDetails getUserDetails(String login);
}
