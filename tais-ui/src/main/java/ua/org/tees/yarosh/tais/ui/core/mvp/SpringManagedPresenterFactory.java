package ua.org.tees.yarosh.tais.ui.core.mvp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import ua.org.tees.yarosh.tais.ui.core.UIContext;

import static ua.org.tees.yarosh.tais.ui.core.bindings.Qualifiers.PRESENTER_FACTORY;

@Service
@Scope("prototype")
@Qualifier(PRESENTER_FACTORY)
public class SpringManagedPresenterFactory implements PresenterFactory {

    private UIContext ctx;

    @Autowired
    public SpringManagedPresenterFactory(UIContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public <P extends Presenter> P getPresenter(Class<P> clazz) {
        return ctx.getBean(clazz);
    }
}
