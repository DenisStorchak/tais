package ua.org.tees.yarosh.tais.ui.core;

import ua.org.tees.yarosh.tais.ui.core.api.SidebarManagerFactory;
import ua.org.tees.yarosh.tais.ui.listeners.SidebarManager;

public class LazySidebarManagerFactory implements SidebarManagerFactory {

    private SidebarManager sidebarManager;

    @Override
    public SidebarManager getSidebarManager() {
        if (sidebarManager == null) {
            sidebarManager = new SidebarManager();
        }
        return sidebarManager;
    }
}
