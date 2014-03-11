package ua.org.tees.yarosh.tais.core.common.dto;

import ua.org.tees.yarosh.tais.core.common.api.Message;

/**
 * @author Timur Yarosh
 *         Date: 09.03.14
 *         Time: 22:30
 */
public class MailMessage implements Message {
    private String message;
    private String from;
    private String to;

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public void setMessage(byte[] bytes) {
        message = new String(bytes); // todo make smth with charset?
    }

    @Override
    public String getFrom() {
        return from;
    }

    @Override
    public void setFrom(String from) {
        this.from = from;
    }

    @Override
    public String getTo() {
        return to;
    }

    @Override
    public void setTo(String to) {
        this.to = to;
    }
}
