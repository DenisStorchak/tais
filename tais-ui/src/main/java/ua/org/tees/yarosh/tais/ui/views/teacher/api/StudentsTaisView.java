package ua.org.tees.yarosh.tais.ui.views.teacher.api;

import com.vaadin.navigator.View;
import ua.org.tees.yarosh.tais.core.common.models.Registrant;
import ua.org.tees.yarosh.tais.ui.core.api.Updatable;
import ua.org.tees.yarosh.tais.ui.core.mvp.Presenter;

import java.util.List;

public interface StudentsTaisView extends View, Updatable {

    void setStudents(List<Registrant> registrants);

    interface StudentsTaisPresenter extends Presenter {
        void onDetails(Registrant registrant);

        void onSend(Registrant registrant);
    }
}
