package ua.org.tees.yarosh.tais.ui.components.windows;

import com.vaadin.data.Item;
import com.vaadin.ui.*;
import org.apache.commons.lang3.RandomStringUtils;
import ua.org.tees.yarosh.tais.core.common.api.Message;
import ua.org.tees.yarosh.tais.ui.components.PlainBorderlessTable;
import ua.org.tees.yarosh.tais.ui.core.api.AbstractWindow;
import ua.org.tees.yarosh.tais.ui.core.api.TaisWindow;

import static ua.org.tees.yarosh.tais.ui.core.VaadinUtils.createSingleFormLayout;
import static ua.org.tees.yarosh.tais.ui.core.VaadinUtils.transformToIconOnlyButton;

@TaisWindow("Чат")
public class ChatWindow extends AbstractWindow {

    private static final String METADATA_CAPTION = "";
    private static final String MESSAGE_CAPTION = " ";
    private static final int RANDOM_LENGTH = 6;
    private Table messages = new PlainBorderlessTable("Чат");
    private TextArea newMessageArea = new TextArea();
    private Button sendButton = new Button();

    @Override
    public void init() {
        super.init();
        transformToIconOnlyButton("Отправить сообщение", "icon-doc-new", null, sendButton); //todo click listener
        messages.addContainerProperty(METADATA_CAPTION, Label.class, null);
        messages.addContainerProperty(MESSAGE_CAPTION, Label.class, null);
    }

    @Override
    protected void fillOutLayout(VerticalLayout contentLayout) {
        contentLayout.addComponent(messages);
        contentLayout.addComponent(createSingleFormLayout(newMessageArea, sendButton));
    }

    public void addOwnMessage(Message<String> message) {
        Item item = messages.addItem(RandomStringUtils.random(RANDOM_LENGTH));
        item.getItemProperty(METADATA_CAPTION).setValue(extractOwnMetadata(message));
        item.getItemProperty(MESSAGE_CAPTION).setValue(message.getMessage());
    }

    private Label extractOwnMetadata(Message<String> message) {
        return null;//todo
    }

    public void addCompanionMessage(Message<String> message) {
        Item item = messages.addItem(RandomStringUtils.random(RANDOM_LENGTH));
        item.getItemProperty(METADATA_CAPTION).setValue(extractCompanionMetadata(message));
        item.getItemProperty(MESSAGE_CAPTION).setValue(message.getMessage());
    }

    private Label extractCompanionMetadata(Message<String> message) {
        return null;//todo
    }
}
