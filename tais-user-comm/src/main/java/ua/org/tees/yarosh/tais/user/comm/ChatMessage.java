package ua.org.tees.yarosh.tais.user.comm;

import ua.org.tees.yarosh.tais.core.common.api.Message;

import java.util.Date;

public class ChatMessage implements Message<String> {

    private String message;
    private String from;
    private String to;
    private Date timestamp;

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public void setMessage(String message) {
        this.message = message;
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

    @Override
    public void setTimestamp(Date date) {
        this.timestamp = date;
    }

    @Override
    public Date getTimestamp() {
        return timestamp;
    }
}
