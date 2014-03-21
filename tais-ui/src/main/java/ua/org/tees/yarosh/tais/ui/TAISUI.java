package ua.org.tees.yarosh.tais.ui;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.org.tees.yarosh.tais.ui.roles.HelpManager;
import ua.org.tees.yarosh.tais.ui.roles.HelpOverlay;
import ua.org.tees.yarosh.tais.ui.roles.MyConverterFactory;
import ua.org.tees.yarosh.tais.ui.roles.Sidebar;
import ua.org.tees.yarosh.tais.ui.roles.teacher.TeacherDashboardView;

import static ua.org.tees.yarosh.tais.ui.core.Messages.WELCOME_MESSAGE;
import static ua.org.tees.yarosh.tais.ui.core.UriFragments.Teacher.TEACHER_DASHBOARD;

/**
 * @author Timur Yarosh
 *         Date: 21.03.14
 *         Time: 19:04
 */
@Theme("dashboard")
@Title("TEES Dashboard")
public class TAISUI extends UI {

    public static final Logger LOGGER = LoggerFactory.getLogger(TAISUI.class);

    CssLayout root = new CssLayout();

    VerticalLayout loginLayout;

    CssLayout menu = new CssLayout();
    CssLayout content = new CssLayout();

    private Navigator nav;

    private HelpManager helpManager;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        LOGGER.info("Session locale is [{}]", VaadinSession.getCurrent().getLocale());
        getSession().setConverterFactory(new MyConverterFactory());
        helpManager = new HelpManager(this);

        setLocale(VaadinSession.getCurrent().getLocale());

        setContent(root);
        root.addStyleName("root");
        root.setSizeFull();

        Label bg = new Label();
        bg.setSizeUndefined();
        bg.addStyleName("login-bg");
        root.addComponent(bg);
        HelpOverlay helpOverlay = helpManager.addOverlay("TAIS", WELCOME_MESSAGE, "login");
        helpOverlay.center();
        addWindow(helpOverlay);

        root.addComponent(new HorizontalLayout() {
            {
                setSizeFull();
                addStyleName("main-view");
                addComponent(new Sidebar());
                addComponent(content);
                content.setSizeFull();
                content.addStyleName("view-content");
                setExpandRatio(content, 1);
            }
        });

        nav = new Navigator(this, content);
        nav.addView(TEACHER_DASHBOARD, TeacherDashboardView.class);
        nav.navigateTo(TEACHER_DASHBOARD);
    }
}
