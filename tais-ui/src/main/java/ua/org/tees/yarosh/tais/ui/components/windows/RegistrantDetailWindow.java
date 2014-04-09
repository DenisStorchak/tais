package ua.org.tees.yarosh.tais.ui.components.windows;

import com.vaadin.ui.Window;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import ua.org.tees.yarosh.tais.core.common.models.Registrant;

@Service
@Scope("prototype")
public class RegistrantDetailWindow extends Window {
    private Registrant registrant;

    public void setRegistrant(Registrant registrant) {
        this.registrant = registrant;
    }

    public Registrant getRegistrant() {
        return registrant;
    }
}
