package ua.org.tees.yarosh.tais.ui.views.teacher.api;

import com.vaadin.navigator.View;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.TextField;
import ua.org.tees.yarosh.tais.core.common.models.Discipline;
import ua.org.tees.yarosh.tais.core.common.models.StudentGroup;
import ua.org.tees.yarosh.tais.homework.models.Question;
import ua.org.tees.yarosh.tais.ui.core.api.Updatable;
import ua.org.tees.yarosh.tais.ui.core.mvp.Presenter;

import java.util.List;

public interface CreateQuestionsSuiteTais extends View, Updatable {

    List<Question> questions();

    interface CreateQuestionsSuitePresenter extends Presenter {
        boolean createQuestionsSuite(ComboBox studentGroup,
                                     TextField theme,
                                     ComboBox discipline,
                                     List<Question> questions,
                                     DateField deadline,
                                     ComboBox enabled);

        List<StudentGroup> studentGroups();

        List<Discipline> disciplines();

        void addQuestion(Question question);
    }
}
