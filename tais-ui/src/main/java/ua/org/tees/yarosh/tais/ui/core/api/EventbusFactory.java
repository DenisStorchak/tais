package ua.org.tees.yarosh.tais.ui.core.api;

import com.google.common.eventbus.AsyncEventBus;

public interface EventbusFactory {
    AsyncEventBus getEventBus();
}
