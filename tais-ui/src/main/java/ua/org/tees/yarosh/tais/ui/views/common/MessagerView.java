package ua.org.tees.yarosh.tais.ui.views.common;

import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.TextArea;
import org.springframework.beans.factory.annotation.Qualifier;
import ua.org.tees.yarosh.tais.auth.api.annotations.PermitRoles;
import ua.org.tees.yarosh.tais.core.common.models.Registrant;
import ua.org.tees.yarosh.tais.ui.components.layouts.DashPanel;
import ua.org.tees.yarosh.tais.ui.components.layouts.DashboardView;
import ua.org.tees.yarosh.tais.ui.core.UIFactory;
import ua.org.tees.yarosh.tais.ui.core.mvp.PresentedBy;
import ua.org.tees.yarosh.tais.ui.core.mvp.TaisView;
import ua.org.tees.yarosh.tais.ui.views.common.api.MessagerTaisView;
import ua.org.tees.yarosh.tais.user.comm.ChatMessage;

import java.util.Date;
import java.util.List;

import static ua.org.tees.yarosh.tais.core.common.dto.Roles.ADMIN;
import static ua.org.tees.yarosh.tais.ui.core.VaadinUtils.createSingleFormLayout;
import static ua.org.tees.yarosh.tais.ui.core.api.DataBinds.UriFragments.Admin.MESSAGER;
import static ua.org.tees.yarosh.tais.ui.views.common.api.MessagerTaisView.MessagerPresenter;

/**
 * @author Timur Yarosh
 *         Date: 23.04.14
 *         Time: 20:58
 */
@Qualifier(MESSAGER)
@PresentedBy(MessagerPresenter.class)
@TaisView("Чатик")
@PermitRoles(ADMIN)
public class MessagerView extends DashboardView implements MessagerTaisView {

    private ComboBox from = new ComboBox();
    private ComboBox to = new ComboBox();
    private TextArea messageArea = new TextArea();
    private Button sendButton = new Button("Отправить");

    public MessagerView() {
        super();

        DashPanel dashPanel = addDashPanel(null, null);
        dashPanel.addComponent(createSingleFormLayout(from, to));
        dashPanel.addComponent(messageArea);
        dashPanel.addComponent(sendButton);
    }

    @Override
    public void init() {
        super.init();

        sendButton.addClickListener(this::sendMessage);
    }

    private void sendMessage(Button.ClickEvent event) {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setMessage(messageArea.getValue());
        chatMessage.setTimestamp(new Date());
        chatMessage.setFrom(((Registrant) from.getValue()).getLogin());
        chatMessage.setTo(((Registrant) to.getValue()).getLogin());

        MessagerPresenter presenter = UIFactory.getCurrent().getRelativePresenter(this, MessagerPresenter.class);
        presenter.sendMessage(chatMessage);

        messageArea.setValue("");
    }

    @Override
    public void setRegistrants(List<Registrant> registrants) {
        from.removeAllItems();
        to.removeAllItems();
        registrants.forEach(r -> {
            from.addItem(r);
            to.addItem(r);
        });
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

    }
}
