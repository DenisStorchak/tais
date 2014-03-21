package ua.org.tees.yarosh.tais.ui.roles.student.views;

import com.vaadin.navigator.ViewChangeListener;
import ua.org.tees.yarosh.tais.ui.core.mvp.AbstractPresenter;
import ua.org.tees.yarosh.tais.ui.core.mvp.PresenterClass;
import ua.org.tees.yarosh.tais.ui.roles.student.api.PersonalWorktableView;
import ua.org.tees.yarosh.tais.ui.roles.student.presenters.PersonalWorktablePresenter;

import java.util.List;

@PresenterClass(PersonalWorktablePresenter.class)
public class PersonalWorktable implements PersonalWorktableView {

    private List<PersonalWorktableListener> listeners;

    @Override
    public void addPresenter(AbstractPresenter presenter) {

    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}
