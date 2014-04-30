package ua.org.tees.yarosh.tais.user.comm;

import ua.org.tees.yarosh.tais.user.comm.api.Communicator;

public class ChatMessageReceivedEvent {

    private ChatMessage chatMessage;
    private Communicator communicator;

    public ChatMessage getChatMessage() {
        return chatMessage;
    }

    public Communicator getCommunicator() {
        return communicator;
    }

    public ChatMessageReceivedEvent(ChatMessage chatMessage, Communicator communicator) {
        this.chatMessage = chatMessage;
        this.communicator = communicator;
    }
}
