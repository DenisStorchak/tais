package ua.org.tees.yarosh.tais.ui.core.components;

import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.NativeButton;

/**
 * @author Timur Yarosh
 *         Date: 22.03.14
 *         Time: 11:39
 */
public class MainView extends HorizontalLayout {
    public MainView(CssLayout content) {
        setSizeFull();
        addStyleName("main-view");
        Sidebar sidebar = new Sidebar();
        SidebarMenu menu = new SidebarMenu();

        NativeButton teacherPanelButton = new NativeButton("Задания");
        teacherPanelButton.addStyleName("icon-dashboard");
        menu.addComponent(teacherPanelButton);

        NativeButton studentsListButton = new NativeButton("Студенты");
        studentsListButton.addStyleName("icon-users");
        menu.addComponent(studentsListButton);

        sidebar.setSidebarMenu(menu);
        sidebar.setUserMenu(new UserMenu("Тимур", "Ярош"));
        addComponent(sidebar);
        addComponent(content);
        content.setSizeFull();
        content.addStyleName("view-content");
        setExpandRatio(content, 1);
    }
}
