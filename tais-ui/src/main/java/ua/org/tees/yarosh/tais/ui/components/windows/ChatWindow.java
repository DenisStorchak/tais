package ua.org.tees.yarosh.tais.ui.components.windows;

import com.vaadin.data.Item;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import ua.org.tees.yarosh.tais.ui.components.PlainBorderlessTable;
import ua.org.tees.yarosh.tais.ui.components.layouts.DashPanel;
import ua.org.tees.yarosh.tais.ui.core.api.AbstractWindow;
import ua.org.tees.yarosh.tais.ui.core.api.Registrants;
import ua.org.tees.yarosh.tais.ui.core.api.TaisWindow;
import ua.org.tees.yarosh.tais.user.comm.ChatMessage;
import ua.org.tees.yarosh.tais.user.comm.ChatService;
import ua.org.tees.yarosh.tais.user.comm.api.Communicator;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.vaadin.event.ShortcutAction.KeyCode.ENTER;
import static com.vaadin.server.Sizeable.Unit.PIXELS;
import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static ua.org.tees.yarosh.tais.ui.core.VaadinUtils.createSingleFormLayout;
import static ua.org.tees.yarosh.tais.ui.core.VaadinUtils.transformToIconOnlyButton;

@TaisWindow
public class ChatWindow extends AbstractWindow {

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

    private Table messages = new PlainBorderlessTable(EMPTY);
    private DashPanel messagesDash = new DashPanel();
    private TextArea newMessageArea = new TextArea();
    private Button sendButton = new Button();

    private Communicator communicator;
    private String destination;

    @Autowired
    public void setCommunicator(@Qualifier(ChatService.QUALIFIER) Communicator communicator) {
        this.communicator = communicator;
    }

    public void setDestination(String destination) {
        this.destination = destination;
        setCaption(format(WINDOW_CAPTION_TEMPLATE, destination));
    }

    @Override
    public void init() {
        super.init();
        setModal(false);

        messages.addContainerProperty(METADATA_CAPTION, Label.class, null);
        messages.addContainerProperty(MESSAGE_CAPTION, Label.class, null);
        messages.setColumnWidth(METADATA_CAPTION, METADATA_WIDTH);
        messages.setColumnWidth(MESSAGE_CAPTION, MESSAGES_WIDTH - METADATA_WIDTH);
        messages.setHeight(MESSAGES_HEIGHT, PIXELS);

        messagesDash.addComponent(messages);
        messagesDash.setHeight(MESSAGES_HEIGHT + SHORT_DISTANCE, PIXELS);
//        messagesPanel.setContent(messages);
//        messagesPanel.setSizeFull();

        newMessageArea.setWidth(MESSAGES_WIDTH, PIXELS);

        sendButton.setWidth(newMessageArea.getHeight(), newMessageArea.getHeightUnits());
        sendButton.setClickShortcut(ENTER);
        transformToIconOnlyButton(SEND_BUTTON_DESCRIPTION, SEND_BUTTON_STYLE, this::sendMessage, sendButton);
    }

    private void sendMessage(Button.ClickEvent event) {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setFrom(Registrants.getCurrent().getLogin());
        chatMessage.setTo(destination);
        chatMessage.setMessage(newMessageArea.getValue());
        chatMessage.setTimestamp(new Date());

        communicator.sendMessage(chatMessage);
        addOwnMessage(chatMessage);

        newMessageArea.setValue("");
    }

    @Override
    protected void fillOutLayout(VerticalLayout contentLayout) {
        contentLayout.addComponent(messagesDash);
        contentLayout.addComponent(createSingleFormLayout(newMessageArea, sendButton));
    }

    public void addOwnMessage(ChatMessage message) {
        if (destination == null) {
            throw new IllegalStateException(NO_DESTINATION_ERROR);
        }
        addMessage(message, OWN_COLOR, Registrants.getCurrent().getLogin());
    }

    public void addCompanionMessage(ChatMessage message) {
        if (destination == null) {
            throw new IllegalStateException(NO_DESTINATION_ERROR);
        }
        addMessage(message, COMPANION_COLOR, destination);
    }

    private void addMessage(ChatMessage message, String color, String author) {
        Item item = messages.addItem(RandomStringUtils.random(RANDOM_LENGTH));
        item.getItemProperty(METADATA_CAPTION).setValue(createMetadataLabel(message, color, author));
        item.getItemProperty(MESSAGE_CAPTION).setValue(createMessageLabel(message.getMessage()));
    }

    private Label createMessageLabel(String message) {
        return new Label(message, ContentMode.HTML);
    }

    private Label createMetadataLabel(ChatMessage message, String color, String author) {
        return new Label(
                format(METADATA_HTML_TEMPLATE, color, SDF.format(message.getTimestamp()), author).replace("\n", BR),
                ContentMode.HTML);
    }
}
