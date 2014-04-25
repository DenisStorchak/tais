package ua.org.tees.yarosh.tais.ui.components.layouts;

import com.vaadin.data.Item;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import org.apache.commons.lang3.RandomStringUtils;
import ua.org.tees.yarosh.tais.ui.components.PlainBorderlessTable;
import ua.org.tees.yarosh.tais.ui.core.api.Registrants;
import ua.org.tees.yarosh.tais.user.comm.ChatMessage;
import ua.org.tees.yarosh.tais.user.comm.api.Communicator;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.vaadin.event.ShortcutAction.KeyCode.ENTER;
import static com.vaadin.server.Sizeable.Unit.PIXELS;
import static java.lang.String.format;
import static ua.org.tees.yarosh.tais.ui.core.VaadinUtils.createSingleFormLayout;
import static ua.org.tees.yarosh.tais.ui.core.VaadinUtils.transformToIconOnlyButton;

public class ChatLayout extends VerticalLayout {

    private static final String METADATA_CAPTION = "";
    private static final String MESSAGE_CAPTION = " ";
    private static final int RANDOM_LENGTH = 6;
    private static final String METADATA_HTML_TEMPLATE = "<font color=%s><b>[%s] %s </b></font>";
    private static final String SEND_BUTTON_DESCRIPTION = "Отправить сообщение";
    private static final String SEND_BUTTON_STYLE = "icon-doc-new";
    private static final SimpleDateFormat SDF = new SimpleDateFormat("hh:mm:ss");
    private static final String OWN_COLOR = "4d7198";
    private static final String COMPANION_COLOR = "00b9ff";
    private static final int MESSAGES_WIDTH = 500;
    private static final String NO_DESTINATION_ERROR = "Destination is null";
    private static final int METADATA_WIDTH = 100;
    private static final String WINDOW_CAPTION_TEMPLATE = "Чат с %s";
    private static final int MESSAGES_HEIGHT = 500;
    private static final String BR = "<br/>";
    private static final int SHORT_DISTANCE = 50;

    private Table dialog = new PlainBorderlessTable("");
    private DashPanel dialogPanel = new DashPanel();
    private TextArea inputArea = new TextArea();
    private Button sendButton = new Button();

    private Communicator communicator;

    private String destination;

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public ChatLayout(Communicator communicator) {
        this.communicator = communicator;

        dialog.addContainerProperty(METADATA_CAPTION, Label.class, null);
        dialog.addContainerProperty(MESSAGE_CAPTION, TextArea.class, null);
        dialog.setColumnWidth(METADATA_CAPTION, METADATA_WIDTH);
        dialog.setColumnWidth(MESSAGE_CAPTION, MESSAGES_WIDTH - METADATA_WIDTH);
        dialog.setHeight(MESSAGES_HEIGHT, PIXELS);

        dialogPanel.addComponent(dialog);
        dialogPanel.setHeight(MESSAGES_HEIGHT, PIXELS);

        transformToIconOnlyButton(SEND_BUTTON_DESCRIPTION, SEND_BUTTON_STYLE, this::sendMessage, sendButton);
        sendButton.setClickShortcut(ENTER);

        addComponent(dialogPanel);
        addComponent(createSingleFormLayout(inputArea, sendButton));

        inputArea.setWidth(MESSAGES_WIDTH, PIXELS);

        setWidth(MESSAGES_WIDTH + SHORT_DISTANCE, PIXELS);
        setHeight(MESSAGES_HEIGHT + SHORT_DISTANCE, PIXELS);
    }

    private void sendMessage(Button.ClickEvent event) {
        if (destination == null) {
            throw new IllegalStateException("Destination is null");
        }
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setFrom(Registrants.getCurrent().getLogin());
        chatMessage.setTo(destination);
        chatMessage.setMessage(inputArea.getValue());
        chatMessage.setTimestamp(new Date());
        sendMessage(chatMessage);

        inputArea.setValue("");
    }

    public void receiveMessage(ChatMessage chatMessage) {
        addMessage(chatMessage, COMPANION_COLOR);
    }

    public void sendMessage(ChatMessage chatMessage) {
        addMessage(chatMessage, OWN_COLOR);
        communicator.sendMessage(chatMessage);
    }

    private void addMessage(ChatMessage chatMessage, String color) {
        Item item = dialog.addItem(RandomStringUtils.random(RANDOM_LENGTH));
        item.getItemProperty(METADATA_CAPTION).setValue(
                createMetadataLabel(chatMessage, color, chatMessage.getFrom()));
        item.getItemProperty(MESSAGE_CAPTION).setValue(createMessageArea(chatMessage.getMessage()));
    }

    private TextArea createMessageArea(String chatMessage) {
        TextArea replica = new TextArea();
        replica.setValue(chatMessage);
        replica.setReadOnly(true);
        return replica;
    }

    private Label createMetadataLabel(ChatMessage message, String color, String author) {
        return new Label(
                format(METADATA_HTML_TEMPLATE, color, SDF.format(message.getTimestamp()), author).replace("\n", BR),
                ContentMode.HTML);
    }
}
