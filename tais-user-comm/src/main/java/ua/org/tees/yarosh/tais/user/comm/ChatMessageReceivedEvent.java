package ua.org.tees.yarosh.tais.user.comm;

public class ChatMessageReceivedEvent {

    private ChatMessage chatMessage;

    public ChatMessage getChatMessage() {
        return chatMessage;
    }

    public ChatMessageReceivedEvent(ChatMessage chatMessage) {
        this.chatMessage = chatMessage;
    }
}
