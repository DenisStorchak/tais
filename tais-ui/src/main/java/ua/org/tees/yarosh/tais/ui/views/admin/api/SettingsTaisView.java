package ua.org.tees.yarosh.tais.ui.views.admin.api;

import com.vaadin.navigator.View;
import ua.org.tees.yarosh.tais.core.common.properties.*;
import ua.org.tees.yarosh.tais.ui.core.api.UpdatableView;
import ua.org.tees.yarosh.tais.ui.core.mvp.Presenter;

public interface SettingsTaisView extends View, UpdatableView {

    interface SettingsPresenter extends Presenter {
        CacheProperties getCacheProperties();

        DefaultUserProperties getDefaultUserProperties();

        HibernateProperties getHibernateProperties();

        JdbcProperties getJdbcProperties();

        MailProperties getMailProperties();
    }
}
