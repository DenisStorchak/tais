package ua.org.tees.yarosh.tais.ui.components.windows;

import com.vaadin.data.Item;
import com.vaadin.ui.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import ua.org.tees.yarosh.tais.ui.components.PlainBorderlessTable;
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
import static ua.org.tees.yarosh.tais.ui.core.VaadinUtils.createSingleFormLayout;
import static ua.org.tees.yarosh.tais.ui.core.VaadinUtils.transformToIconOnlyButton;

@TaisWindow("Чат")
public class ChatWindow extends AbstractWindow {

    private static final String METADATA_CAPTION = "";
    private static final String MESSAGE_CAPTION = " ";
    private static final int RANDOM_LENGTH = 6;
    private static final String METADATA_HTML_TEMPLATE = "<font color=%s><b>[%s] </b></font>";
    private static final String SEND_BUTTON_DESCRIPTION = "Отправить сообщение";
    private static final String SEND_BUTTON_STYLE = "icon-doc-new";
    private static final SimpleDateFormat SDF = new SimpleDateFormat("hh:mm:ss");
    private static final String OWN_COLOR = "0093ff";
    private static final String COMPANION_COLOR = "46aaf3";
    private Table messages = new PlainBorderlessTable("Чат");
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
    }

    @Override
    public void init() {
        super.init();
        setModal(false);

        transformToIconOnlyButton(SEND_BUTTON_DESCRIPTION, SEND_BUTTON_STYLE, this::sendMessage, sendButton);
        messages.addContainerProperty(METADATA_CAPTION, Label.class, null);
        messages.addContainerProperty(MESSAGE_CAPTION, Label.class, null);

        messages.setWidth(500, PIXELS);
        newMessageArea.setWidth(500, PIXELS);

        sendButton.setClickShortcut(ENTER);
    }

    private void sendMessage(Button.ClickEvent event) {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setFrom(Registrants.getCurrent().getLogin());
        chatMessage.setTo(destination);
        chatMessage.setMessage(newMessageArea.getValue());
        chatMessage.setTimestamp(new Date());

        communicator.sendMessage(chatMessage);

        newMessageArea.setValue("");
    }

    @Override
    protected void fillOutLayout(VerticalLayout contentLayout) {
        contentLayout.addComponent(messages);
        contentLayout.addComponent(createSingleFormLayout(newMessageArea, sendButton));
    }

    public void addOwnMessage(ChatMessage message) {
        if (destination == null) {
            throw new IllegalStateException("Destination is null");
        }
        addMessage(message, OWN_COLOR);
    }

    public void addCompanionMessage(ChatMessage message) {
        if (destination == null) {
            throw new IllegalStateException("Destination is null");
        }
        addMessage(message, COMPANION_COLOR);
    }

    private void addMessage(ChatMessage message, String color) {
        Item item = messages.addItem(RandomStringUtils.random(RANDOM_LENGTH));
        item.getItemProperty(METADATA_CAPTION).setValue(createMetadataLabel(message, color));
        item.getItemProperty(MESSAGE_CAPTION).setValue(message.getMessage());
    }

    private Label createMetadataLabel(ChatMessage message, String color) {
        return new Label(String.format(METADATA_HTML_TEMPLATE, color, SDF.format(message.getTimestamp())));
    }
}
