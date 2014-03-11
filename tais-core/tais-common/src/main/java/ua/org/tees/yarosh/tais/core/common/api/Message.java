package ua.org.tees.yarosh.tais.core.common.api;

public interface Message {
    String getMessage();

    void setMessage(String message);

    void setMessage(byte[] bytes);

    String getFrom();

    void setFrom(String from);

    String getTo();

    void setTo(String to);
}
