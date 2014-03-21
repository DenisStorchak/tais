package ua.org.tees.yarosh.tais.ui.roles.teacher;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;
import ua.org.tees.yarosh.tais.ui.core.Messages;
import ua.org.tees.yarosh.tais.ui.roles.teacher.windows.CreateTaskWindow;


/**
 * @author Timur Yarosh
 *         Date: 21.03.14
 *         Time: 19:57
 */
public class TeacherDashboardView extends VerticalLayout implements View {

    public TeacherDashboardView() {
        setSizeFull();
        addStyleName("dashboard-view");
        HorizontalLayout top = new HorizontalLayout();
        top.setWidth("100%");
        top.setSpacing(true);
        top.addStyleName("toolbar");
        addComponent(top);
        Label title = new Label("Панель преподавателя");
        title.setSizeUndefined();
        title.addStyleName("h1");
        top.addComponent(title);
        top.setComponentAlignment(title, Alignment.MIDDLE_LEFT);
        top.setExpandRatio(title, 1);

        Button createTask = new Button();
        createTask.addStyleName("icon-edit");
        createTask.addStyleName("icon-only");
        createTask.setDescription(Messages.CREATE_TASK_BUTTON_DESCRIPTION);
        createTask.addClickListener(clickEvent -> getUI().addWindow(new CreateTaskWindow()));
        top.addComponent(createTask);
        top.setComponentAlignment(createTask, Alignment.MIDDLE_LEFT);

        HorizontalLayout row = new HorizontalLayout();
        row.setSizeFull();
        row.setMargin(new MarginInfo(true, true, false, true));
        row.setSpacing(true);
        addComponent(row);
        setExpandRatio(row, 1.5f);

        row.addComponent(createPanel());
    }

    private CssLayout createPanel() {
        CssLayout panel = new CssLayout();
        panel.addStyleName("layout-panel");
        panel.setSizeFull();

        Button configure = new Button();
        configure.addStyleName("configure");
        configure.addStyleName("icon-cog");
        configure.addStyleName("icon-only");
        configure.addStyleName("borderless");
        configure.setDescription("Configure");
        configure.addStyleName("small");
        configure.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                Notification.show("Not implemented in this demo");
            }
        });
        panel.addComponent(configure);
        return panel;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}
