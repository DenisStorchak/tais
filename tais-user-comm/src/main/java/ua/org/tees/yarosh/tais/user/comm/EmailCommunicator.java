package ua.org.tees.yarosh.tais.user.comm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import ua.org.tees.yarosh.tais.core.common.api.Message;
import ua.org.tees.yarosh.tais.user.comm.api.Communicator;

/**
 * @author Timur Yarosh
 *         Date: 09.03.14
 *         Time: 22:32
 */
@Service
public class EmailCommunicator implements Communicator {
    @Autowired
    private MailSender mailSender;
    @Autowired
    private SimpleMailMessage templateMailMessage;

    @Override
    public void sendMessage(Message message) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage(templateMailMessage);
        simpleMailMessage.setTo(message.getTo());
        simpleMailMessage.setText(formatMessage(message));
        mailSender.send(simpleMailMessage);
    }

    private String formatMessage(Message message) {
        return String.format("%s\nОт: %s", message.getFrom());
    }
}
