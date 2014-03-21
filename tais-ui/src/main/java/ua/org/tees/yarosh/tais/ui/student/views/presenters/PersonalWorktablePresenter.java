package ua.org.tees.yarosh.tais.ui.student.views.presenters;

import com.vaadin.server.VaadinSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.org.tees.yarosh.tais.core.common.exceptions.RegistrantNotFoundException;
import ua.org.tees.yarosh.tais.core.common.models.Registrant;
import ua.org.tees.yarosh.tais.core.user.mgmt.api.service.RegistrantService;
import ua.org.tees.yarosh.tais.homework.models.ManualTask;
import ua.org.tees.yarosh.tais.ui.core.mvp.AbstractPresenter;
import ua.org.tees.yarosh.tais.ui.student.views.api.PersonalWorktableView;
import ua.org.tees.yarosh.tais.ui.student.views.api.TaisView;

import java.util.List;

import static ua.org.tees.yarosh.tais.ui.SessionAttributes.REGISTRANT_ID;

@Service
public class PersonalWorktablePresenter extends AbstractPresenter implements PersonalWorktableView.PersonalWorktableListener {

    private RegistrantService registrantService;

    public PersonalWorktablePresenter(TaisView view) {
        super(view);
    }

    @Autowired
    public void setRegistrantService(RegistrantService registrantService) {
        this.registrantService = registrantService;
    }

    @Override
    public List<ManualTask> listActualUnresolvedTasks() {
        String registrantId = (String) VaadinSession.getCurrent().getAttribute(REGISTRANT_ID);
        Registrant registrant;
        try {
            registrant = registrantService.getRegistration(registrantId);
        } catch (RegistrantNotFoundException e) {
            info(e.getMessage());
        }
        return null;
    }

    @Override
    protected void initView() {
        getView().addPresenter(this);
    }
}
