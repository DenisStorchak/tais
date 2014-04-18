package ua.org.tees.yarosh.tais.ui.views.teacher.api;

import com.vaadin.navigator.View;
import ua.org.tees.yarosh.tais.core.common.models.Discipline;
import ua.org.tees.yarosh.tais.core.common.models.StudentGroup;
import ua.org.tees.yarosh.tais.homework.models.ManualTask;
import ua.org.tees.yarosh.tais.ui.components.PayloadReceiver;
import ua.org.tees.yarosh.tais.ui.core.api.Initable;
import ua.org.tees.yarosh.tais.ui.core.api.Updateable;
import ua.org.tees.yarosh.tais.ui.core.mvp.Presenter;

import java.util.List;

public interface CreateManualTaskTaisView extends View, Updateable {

    void setGroups(List<StudentGroup> studentGroups);

    void setDisciplines(List<Discipline> disciplines);

    void setPayloadReceiver(PayloadReceiver payloadReceiver);

    void setPayloadPath(String path);

    interface CreateManualTaskPresenter extends Presenter, Initable {
        void onCreate(ManualTask manualTask);
    }
}
