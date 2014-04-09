package ua.org.tees.yarosh.tais.ui.views.teacher.api;

import com.vaadin.navigator.View;
import ua.org.tees.yarosh.tais.core.common.models.Discipline;
import ua.org.tees.yarosh.tais.core.common.models.StudentGroup;
import ua.org.tees.yarosh.tais.homework.models.ManualTask;
import ua.org.tees.yarosh.tais.ui.core.api.Initable;
import ua.org.tees.yarosh.tais.ui.core.api.Updatable;
import ua.org.tees.yarosh.tais.ui.core.mvp.Presenter;

import java.util.List;

import static ua.org.tees.yarosh.tais.ui.views.teacher.presenters.CreateManualTaskListener.PayloadReceiver;

public interface CreateManualTaskTaisView extends View, Updatable {

    void setGroups(List<StudentGroup> studentGroups);

    void setDisciplines(List<Discipline> disciplines);

    void setPayloadReceiver(PayloadReceiver payloadReceiver);

    interface CreateManualTaskPresenter extends Presenter, Initable {
        void onCreate(ManualTask manualTask);
    }
}
