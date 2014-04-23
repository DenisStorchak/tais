package ua.org.tees.yarosh.tais.ui.views.common.presenters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import ua.org.tees.yarosh.tais.core.user.mgmt.api.service.RegistrantService;
import ua.org.tees.yarosh.tais.ui.core.api.Updateable;
import ua.org.tees.yarosh.tais.ui.core.mvp.AbstractPresenter;
import ua.org.tees.yarosh.tais.ui.core.mvp.TaisPresenter;
import ua.org.tees.yarosh.tais.ui.views.common.api.MessagerTaisView;
import ua.org.tees.yarosh.tais.user.comm.ChatMessage;
import ua.org.tees.yarosh.tais.user.comm.ChatService;
import ua.org.tees.yarosh.tais.user.comm.api.Communicator;

import static ua.org.tees.yarosh.tais.ui.core.api.DataBinds.Qualifiers.MESSAGER;
import static ua.org.tees.yarosh.tais.ui.views.common.api.MessagerTaisView.MessagerPresenter;

/**
 * @author Timur Yarosh
 *         Date: 23.04.14
 *         Time: 21:13
 */
@TaisPresenter
public class MessagerListener extends AbstractPresenter implements MessagerPresenter {

    private Communicator communicator;
    private RegistrantService registrantService;

    @Autowired
    public void setCommunicator(@Qualifier(ChatService.QUALIFIER) Communicator communicator) {
        this.communicator = communicator;
    }

    @Autowired
    public void setRegistrantService(RegistrantService registrantService) {
        this.registrantService = registrantService;
    }

    @Autowired
    public MessagerListener(@Qualifier(MESSAGER) Updateable view) {
        super(view);
    }

    @Override
    public void init() {
        MessagerTaisView view = getView(MessagerTaisView.class);
        view.setRegistrants(registrantService.findAllRegistrants());
    }

    @Override
    public void sendMessage(ChatMessage chatMessage) {
        communicator.sendMessage(chatMessage);
    }
}
