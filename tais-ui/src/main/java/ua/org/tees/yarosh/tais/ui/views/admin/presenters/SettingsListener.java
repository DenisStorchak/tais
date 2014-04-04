package ua.org.tees.yarosh.tais.ui.views.admin.presenters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import ua.org.tees.yarosh.tais.core.common.properties.*;
import ua.org.tees.yarosh.tais.ui.core.UIContext;
import ua.org.tees.yarosh.tais.ui.core.mvp.AbstractPresenter;
import ua.org.tees.yarosh.tais.ui.core.mvp.UpdatableView;

import static ua.org.tees.yarosh.tais.ui.core.DataBinds.UriFragments.Admin.SETTINGS;
import static ua.org.tees.yarosh.tais.ui.views.admin.api.SettingsTaisView.SettingsPresenter;

@Service
@Scope("prototype")
public class SettingsListener extends AbstractPresenter implements SettingsPresenter {

    private UIContext uiContext;

    @Autowired
    public void setUiContext(UIContext uiContext) {
        this.uiContext = uiContext;
    }

    @Autowired
    public SettingsListener(@Qualifier(SETTINGS) UpdatableView view) {
        super(view);
    }

    @Override
    public CacheProperties getCacheProperties() {
        return uiContext.getBean(CacheProperties.class);
    }

    @Override
    public DefaultUserProperties getDefaultUserProperties() {
        return uiContext.getBean(DefaultUserProperties.class);
    }

    @Override
    public HibernateProperties getHibernateProperties() {
        return uiContext.getBean(HibernateProperties.class);
    }

    @Override
    public JdbcProperties getJdbcProperties() {
        return uiContext.getBean(JdbcProperties.class);
    }

    @Override
    public MailProperties getMailProperties() {
        return uiContext.getBean(MailProperties.class);
    }
}
