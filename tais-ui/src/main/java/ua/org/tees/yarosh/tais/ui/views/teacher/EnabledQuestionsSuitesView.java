package ua.org.tees.yarosh.tais.ui.views.teacher;

import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Qualifier;
import ua.org.tees.yarosh.tais.auth.annotations.PermitRoles;
import ua.org.tees.yarosh.tais.core.common.models.StudentGroup;
import ua.org.tees.yarosh.tais.homework.models.QuestionsSuite;
import ua.org.tees.yarosh.tais.ui.components.PlainBorderlessTable;
import ua.org.tees.yarosh.tais.ui.components.layouts.DashPanel;
import ua.org.tees.yarosh.tais.ui.components.layouts.DashboardView;
import ua.org.tees.yarosh.tais.ui.core.UIFactoryAccessor;
import ua.org.tees.yarosh.tais.ui.core.mvp.PresentedBy;
import ua.org.tees.yarosh.tais.ui.core.mvp.TaisView;
import ua.org.tees.yarosh.tais.ui.views.teacher.api.EnabledQuestionsSuitesTaisView;

import java.util.Date;
import java.util.List;

import static ua.org.tees.yarosh.tais.core.common.dto.Roles.TEACHER;
import static ua.org.tees.yarosh.tais.ui.core.DataBinds.UriFragments.Teacher.ENABLED_QUESTIONS;
import static ua.org.tees.yarosh.tais.ui.views.teacher.api.EnabledQuestionsSuitesTaisView.EnabledQuestionsSuitesPresenter;

@PresentedBy(EnabledQuestionsSuitesPresenter.class)
@PermitRoles(TEACHER)
@Qualifier(ENABLED_QUESTIONS)
@TaisView("Все тесты")
public class EnabledQuestionsSuitesView extends DashboardView implements EnabledQuestionsSuitesTaisView {

    private static final String PROPERTY_GROUP = "Группа";
    private static final String PROPERTY_DISCIPLINE = "Дисциплина";
    private static final String PROPERTY_THEME = "Тема";
    private static final String PROPERTY_DEADLINE = "Дедлайн";
    private static final String PROPERTY_INTERACTION = "Взаимодействие";

    private ComboBox studentGroups = new ComboBox();
    private Button search = new Button("Поиск тестов");
    private Table contentTable = new PlainBorderlessTable("Тесты");

    public EnabledQuestionsSuitesView() {
        top.addComponents(studentGroups, search);
        DashPanel dashPanel = addDashPanel(null, null, contentTable);
        dashPanel.setSizeUndefined();
        dashPanel.setWidth(80, Unit.PERCENTAGE);
        dash.setComponentAlignment(dashPanel, Alignment.TOP_CENTER);

        search.setDescription("Поиск тестов");
        search.addClickListener(event -> UIFactoryAccessor.getCurrent()
                .getRelativePresenter(this, EnabledQuestionsSuitesPresenter.class)
                .onSearch((StudentGroup) studentGroups.getValue()));
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }

    @Override
    public void setSuites(List<QuestionsSuite> questionsSuites) {
        Container container = new IndexedContainer();
        container.addContainerProperty(PROPERTY_GROUP, String.class, null);
        container.addContainerProperty(PROPERTY_DISCIPLINE, String.class, null);
        container.addContainerProperty(PROPERTY_THEME, String.class, null);
        container.addContainerProperty(PROPERTY_DEADLINE, Date.class, null);
        container.addContainerProperty(PROPERTY_INTERACTION, AbstractLayout.class, null);

        for (QuestionsSuite questionsSuite : questionsSuites) {
            Item item = container.addItem(questionsSuite.getId());
            item.getItemProperty(PROPERTY_GROUP).setValue(questionsSuite.getStudentGroup().toString());
            item.getItemProperty(PROPERTY_DISCIPLINE).setValue(questionsSuite.getDiscipline().toString());
            item.getItemProperty(PROPERTY_THEME).setValue(questionsSuite.getTheme());
            item.getItemProperty(PROPERTY_DEADLINE).setValue(questionsSuite.getDeadline());
            item.getItemProperty(PROPERTY_INTERACTION).setValue(createInteractionLayout(questionsSuite));
        }
        contentTable.setContainerDataSource(container);
    }

    private HorizontalLayout createInteractionLayout(QuestionsSuite questionsSuite) {
        Button deleteButton = createDeleteButton(questionsSuite.getId(), questionsSuite.getStudentGroup());
        Button detailsButton = createDetailsButton(questionsSuite.getId());
        return new HorizontalLayout(deleteButton, detailsButton);
    }

    private Button createDetailsButton(Long id) {
        Button det = new Button("Детали");
        det.setDescription("Открыть детали");
        det.addStyleName("icon-only");
        det.addStyleName("icon-logout");
        det.addClickListener(event -> UIFactoryAccessor.getCurrent()
                .getRelativePresenter(this, EnabledQuestionsSuitesPresenter.class)
                .onDetails(id));
        return det;
    }

    private Button createDeleteButton(Long id, StudentGroup studentGroup) {
        Button del = new Button("Удалить");
        del.setDescription("Удалить тест");
        del.addStyleName("icon-only");
        del.addStyleName("icon-logout");
        del.addClickListener(event -> UIFactoryAccessor.getCurrent()
                .getRelativePresenter(this, EnabledQuestionsSuitesPresenter.class)
                .onDelete(id, studentGroup));
        return del;
    }

    @Override
    public void setGroups(List<StudentGroup> studentGroups) {
        studentGroups.forEach(this.studentGroups::addItem);
    }
}
