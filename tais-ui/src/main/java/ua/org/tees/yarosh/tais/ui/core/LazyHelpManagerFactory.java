package ua.org.tees.yarosh.tais.ui.core;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import ua.org.tees.yarosh.tais.ui.components.HelpManager;
import ua.org.tees.yarosh.tais.ui.core.api.HelpManagerFactory;

import static ua.org.tees.yarosh.tais.ui.core.DataBinds.Qualifiers.HELP_MANAGER_FACTORY;

@Service
@Scope("prototype")
@Qualifier(HELP_MANAGER_FACTORY)
public class LazyHelpManagerFactory implements HelpManagerFactory {

    private HelpManager helpManager;

    @Override
    public HelpManager getHelpManager() {
        if (helpManager == null) {
            helpManager = new HelpManager();
        }
        return helpManager;
    }
}
