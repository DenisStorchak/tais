package ua.org.tees.yarosh.tais.ui.views.common.api;

import com.vaadin.navigator.View;
import ua.org.tees.yarosh.tais.core.common.models.Registrant;
import ua.org.tees.yarosh.tais.ui.core.api.Updateable;
import ua.org.tees.yarosh.tais.ui.core.mvp.Presenter;
import ua.org.tees.yarosh.tais.user.comm.ChatMessage;

import java.util.List;

/**
 * @author Timur Yarosh
 *         Date: 23.04.14
 *         Time: 20:55
 */
public interface MessagerTaisView extends View, Updateable {

    void setRegistrants(List<Registrant> registrants);

    interface MessagerPresenter extends Presenter {
        void sendMessage(ChatMessage chatMessage);
    }
}
