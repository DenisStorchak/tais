package ua.org.tees.yarosh.tais.attendance.fprint;

/**
 * @author Timur Yarosh
 *         Date: 20.03.14
 *         Time: 0:12
 */
public class PipeAlreadyExpectingException extends Exception {
    public PipeAlreadyExpectingException(String message) {
        super(message);
    }
}
