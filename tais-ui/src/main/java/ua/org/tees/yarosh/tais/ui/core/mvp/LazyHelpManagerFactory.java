package ua.org.tees.yarosh.tais.ui.core.mvp;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import ua.org.tees.yarosh.tais.ui.core.HelpManager;

import static ua.org.tees.yarosh.tais.ui.core.bindings.Qualifiers.HELP_MANAGER_FACTORY;

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
