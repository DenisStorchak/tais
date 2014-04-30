package ua.org.tees.yarosh.tais.ui.components.windows;

import com.vaadin.ui.Notification;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import ua.org.tees.yarosh.tais.ui.components.layouts.ChatLayout;
import ua.org.tees.yarosh.tais.ui.core.api.AbstractWindow;
import ua.org.tees.yarosh.tais.ui.core.api.TaisWindow;
import ua.org.tees.yarosh.tais.user.comm.ChatMessage;
import ua.org.tees.yarosh.tais.user.comm.SimpleChatService;
import ua.org.tees.yarosh.tais.user.comm.api.Communicator;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.vaadin.server.Sizeable.Unit.PIXELS;
import static com.vaadin.ui.Notification.Type.TRAY_NOTIFICATION;
import static java.lang.String.format;

@TaisWindow
public class ChatWindow extends AbstractWindow {

    private static final int WIDTH = 550;
    private static final String MESSAGE_RECEIVED_TEMPLATE = "Получено сообщение от %s";
    private static final int HEIGHT = 550;

    private TabSheet content = new TabSheet();
    private Map<String, ChatLayout> dialogs = new ConcurrentHashMap<>();

    private Communicator communicator;

    @Autowired
    public void setCommunicator(@Qualifier(SimpleChatService.QUALIFIER) Communicator communicator) {
        this.communicator = communicator;
    }

    @Override
    public void init() {
        super.init();
        setModal(false);
    }

    @Override
    protected void fillOutLayout(VerticalLayout contentLayout) {
        contentLayout.addComponent(content);
    }

    public void receiveMessage(ChatMessage message) {
        if (dialogs.containsKey(message.getFrom())) {
            ChatLayout dialog = dialogs.get(message.getFrom());
            dialog.receiveMessage(message);
        } else {
            addDialog(message);
        }
    }

    private void addDialog(ChatMessage message) {
        ChatLayout chatLayout = new ChatLayout(communicator);
        chatLayout.receiveMessage(message);
        chatLayout.setDestination(message.getFrom());

        content.addTab(chatLayout, message.getFrom());
        content.getTab(chatLayout).setClosable(true);
        content.setWidth(WIDTH, PIXELS);
        content.setHeight(HEIGHT, PIXELS);
        dialogs.put(message.getFrom(), chatLayout);

        Notification.show(format(MESSAGE_RECEIVED_TEMPLATE, message.getFrom()), TRAY_NOTIFICATION);
    }
}
