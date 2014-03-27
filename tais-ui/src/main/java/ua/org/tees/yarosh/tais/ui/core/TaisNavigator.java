package ua.org.tees.yarosh.tais.ui.core;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.UI;

public class TaisNavigator extends Navigator {
    private ViewResolver viewResolver = new ViewResolver();

    public TaisNavigator(UI ui, ComponentContainer container) {
        super(ui, container);
    }

    @Override
    public void addView(String viewName, View view) {
        viewResolver.register(view.getClass(), viewName);
        super.addView(viewName, view);
    }

    @Override
    public void addProvider(com.vaadin.navigator.ViewProvider provider) {
        if (provider instanceof ClassBasedViewProvider) {
            ClassBasedViewProvider classBasedViewProvider = (ClassBasedViewProvider) provider;
            viewResolver.register(classBasedViewProvider.getViewClass(), classBasedViewProvider.getViewName());
        }
        super.addProvider(provider);
    }
}
