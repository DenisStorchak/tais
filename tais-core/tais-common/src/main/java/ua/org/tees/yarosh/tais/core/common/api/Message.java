package ua.org.tees.yarosh.tais.core.common.api;

import java.io.Serializable;
import java.util.Date;

public interface Message<T> extends Serializable {
    T getMessage();

    void setMessage(T message);

    String getFrom();

    void setFrom(String from);

    String getTo();

    void setTo(String to);

    void setTimestamp(Date date);

    Date getTimestamp();
}
