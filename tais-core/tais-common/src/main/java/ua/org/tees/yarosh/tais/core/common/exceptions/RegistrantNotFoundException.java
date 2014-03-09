package ua.org.tees.yarosh.tais.core.common.exceptions;

/**
 * @author Timur Yarosh
 *         Date: 09.03.14
 *         Time: 17:44
 */
public class RegistrantNotFoundException extends Exception {
    public RegistrantNotFoundException(String login) {
        super(String.format("Login %s not found", login));
    }
}
