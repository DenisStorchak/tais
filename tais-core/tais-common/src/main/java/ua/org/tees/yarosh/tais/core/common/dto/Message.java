package ua.org.tees.yarosh.tais.core.common.dto;

/**
 * @author Timur Yarosh
 *         Date: 09.03.14
 *         Time: 22:30
 */
public class Message {
    private String message;
    private String from;
    private String to;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}
