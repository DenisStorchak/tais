package ua.org.tees.yarosh.tais.ui.views.student;

import com.vaadin.data.Item;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
import org.springframework.beans.factory.annotation.Qualifier;
import ua.org.tees.yarosh.tais.auth.annotations.PermitRoles;
import ua.org.tees.yarosh.tais.homework.models.ManualTask;
import ua.org.tees.yarosh.tais.homework.models.QuestionsSuite;
import ua.org.tees.yarosh.tais.ui.components.PlainBorderlessTable;
import ua.org.tees.yarosh.tais.ui.components.layouts.DashboardView;
import ua.org.tees.yarosh.tais.ui.core.mvp.PresentedBy;
import ua.org.tees.yarosh.tais.ui.core.mvp.TaisView;
import ua.org.tees.yarosh.tais.ui.views.student.api.UnresolvedTasksTaisView;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static ua.org.tees.yarosh.tais.core.common.dto.Roles.STUDENT;
import static ua.org.tees.yarosh.tais.ui.core.DataBinds.UriFragments.Student.UNRESOLVED;
import static ua.org.tees.yarosh.tais.ui.core.SessionFactory.getCurrent;
import static ua.org.tees.yarosh.tais.ui.core.VaadinUtils.transformToIconOnlyButton;
import static ua.org.tees.yarosh.tais.ui.views.student.api.UnresolvedTasksTaisView.UnresolvedTasksPresenter;

@Qualifier(UNRESOLVED)
@PresentedBy(UnresolvedTasksPresenter.class)
@PermitRoles(STUDENT)
@TaisView("Невыполненные задания")
public class UnresolvedTasksView extends DashboardView implements UnresolvedTasksTaisView {

    private static final String PROPERTY_DISCIPLINE = "Дисциплина";
    private static final String PROPERTY_DEADLINE = "Дедлайн";
    private static final String PROPERTY_TEACHER = "Преподаватель";
    private static final String PROPERTY_INTERACTION = "Взаимодействие";
    private static final String PROPERTY_THEME = "Тема";
    private static final String PROPERTY_TYPE = "Тип задания";
    private static final String VALUE_QUESTIONS_SUITE = "Тесты";
    private static final String VALUE_MANUAL_TASK = "С файлом";

    private UnresolvedTasksPresenter p;

    private Table unresolvedTasks = new PlainBorderlessTable("Невыполненные задания");
    private Button refresh = new Button();

    @Override
    public void init() {
        super.init();
        p = getCurrent().getRelativePresenter(this, UnresolvedTasksPresenter.class);
        transformToIconOnlyButton("Обновить список", "icon-doc-new", e -> p.onRefresh(), refresh);
    }

    public UnresolvedTasksView() {
        unresolvedTasks.addContainerProperty(PROPERTY_DISCIPLINE, String.class, null);
        unresolvedTasks.addContainerProperty(PROPERTY_THEME, String.class, null);
        unresolvedTasks.addContainerProperty(PROPERTY_DEADLINE, Date.class, null);
        unresolvedTasks.addContainerProperty(PROPERTY_TEACHER, String.class, null);
        unresolvedTasks.addContainerProperty(PROPERTY_TYPE, String.class, null);
        unresolvedTasks.addContainerProperty(PROPERTY_INTERACTION, HorizontalLayout.class, null);

        top.addComponent(refresh);
        addDashPanel(null, null, unresolvedTasks);
    }

    @Override
    public void setUnresolvedManualTasks(List<ManualTask> manualTasks) {
        removeTasks(VALUE_MANUAL_TASK);
        for (ManualTask manualTask : manualTasks) {
            Button detailsButton = new Button();
            transformToIconOnlyButton("Подробнее", "icon-doc-new", e -> p.onManualTask(manualTask), detailsButton);
            HorizontalLayout controls = new HorizontalLayout(detailsButton);

            Item item = unresolvedTasks.addItem(manualTask.getId());
            item.getItemProperty(PROPERTY_DISCIPLINE).setValue(manualTask.getDiscipline().toString());
            item.getItemProperty(PROPERTY_THEME).setValue(manualTask.getTheme());
            item.getItemProperty(PROPERTY_DEADLINE).setValue(manualTask.getDeadline());
            item.getItemProperty(PROPERTY_TEACHER).setValue(manualTask.getExaminer().toString());
            item.getItemProperty(PROPERTY_TYPE).setValue(VALUE_MANUAL_TASK);
            item.getItemProperty(PROPERTY_INTERACTION).setValue(controls);
        }
    }

    @Override
    public void setUnresolvedQuestionsSuites(List<QuestionsSuite> questionsSuites) {
        removeTasks(VALUE_QUESTIONS_SUITE);
        for (QuestionsSuite questionsSuite : questionsSuites) {
            Button details = new Button();
            transformToIconOnlyButton("Подробнее", "icon-doc-new", e -> p.onQuestionsSuite(questionsSuite), details);
            HorizontalLayout controls = new HorizontalLayout(details);

            Item item = unresolvedTasks.addItem(questionsSuite.getId());
            item.getItemProperty(PROPERTY_DISCIPLINE).setValue(questionsSuite.getDiscipline().toString());
            item.getItemProperty(PROPERTY_THEME).setValue(questionsSuite.getTheme());
            item.getItemProperty(PROPERTY_DEADLINE).setValue(questionsSuite.getDeadline());
            item.getItemProperty(PROPERTY_TEACHER).setValue(questionsSuite.getExaminer().toString());
            item.getItemProperty(PROPERTY_TYPE).setValue(VALUE_QUESTIONS_SUITE);
            item.getItemProperty(PROPERTY_INTERACTION).setValue(controls);
        }
    }

    private void removeTasks(String type) {
        List<Object> removeCandidates = unresolvedTasks.getItemIds().stream()
                .filter(id -> unresolvedTasks.getItem(id).getItemProperty(PROPERTY_TYPE)
                        .getValue().equals(type)).map(id -> id).collect(Collectors.toList());
        removeCandidates.forEach(unresolvedTasks::removeItem);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

    }
}
