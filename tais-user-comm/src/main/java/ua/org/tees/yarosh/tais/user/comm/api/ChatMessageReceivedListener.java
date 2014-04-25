package ua.org.tees.yarosh.tais.user.comm.api;

import ua.org.tees.yarosh.tais.core.common.api.Listener;
import ua.org.tees.yarosh.tais.user.comm.ChatMessageReceivedEvent;

public interface ChatMessageReceivedListener extends Listener {
    void onReceived(ChatMessageReceivedEvent event);
}
