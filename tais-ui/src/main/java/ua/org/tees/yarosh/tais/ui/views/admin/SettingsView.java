package ua.org.tees.yarosh.tais.ui.views.admin;

import com.vaadin.data.Item;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import org.springframework.beans.factory.annotation.Qualifier;
import ua.org.tees.yarosh.tais.auth.annotations.PermitRoles;
import ua.org.tees.yarosh.tais.core.common.properties.*;
import ua.org.tees.yarosh.tais.ui.RoleTranslator;
import ua.org.tees.yarosh.tais.ui.components.PlainBorderlessTable;
import ua.org.tees.yarosh.tais.ui.core.DashboardLayout;
import ua.org.tees.yarosh.tais.ui.core.SessionFactory;
import ua.org.tees.yarosh.tais.ui.core.mvp.PresentedBy;
import ua.org.tees.yarosh.tais.ui.core.mvp.TaisView;
import ua.org.tees.yarosh.tais.ui.views.admin.api.SettingsTaisView;

import static java.lang.String.format;
import static org.apache.commons.lang3.RandomStringUtils.random;
import static ua.org.tees.yarosh.tais.core.common.dto.Roles.ADMIN;
import static ua.org.tees.yarosh.tais.ui.core.DataBinds.UriFragments.Admin.SETTINGS;
import static ua.org.tees.yarosh.tais.ui.views.admin.api.SettingsTaisView.SettingsPresenter;

@PresentedBy(SettingsPresenter.class)
@TaisView
@Qualifier(SETTINGS)
@PermitRoles(ADMIN)
@SuppressWarnings("unchecked")
public class SettingsView extends DashboardLayout implements SettingsTaisView {

    private static final String KEY = "Параметр";
    private static final String VALUE = "Значение";
    private static final int ITEM_ID_LENGTH = 6;
    private static final String KEY_HTML = "<b>%s<b>";
    private static final String VALUE_HTML = "<i>%s</i>";

    public SettingsView() {
        super("Настройки", false);
    }

    @Override
    public void update() {
        dash.removeAllComponents();
        SettingsPresenter presenter = SessionFactory.getCurrent().getRelativePresenter(this, SettingsPresenter.class);
        addDashPanel(null, null, createPropertiesTable(presenter.getCacheProperties()));
        addDashPanel(null, null, createPropertiesTable(presenter.getHibernateProperties()));
        addDashPanel(null, null, createPropertiesTable(presenter.getDefaultUserProperties()));
        addDashPanel(null, null, createPropertiesTable(presenter.getJdbcProperties()));
        addDashPanel(null, null, createPropertiesTable(presenter.getMailProperties()));
    }

    private Table createPropertiesTable(CacheProperties cache) {
        Table table = createTemplateTable("Cache");
        addItem(table, "Cache включен", cache.isCacheEnabled() ? "Да" : "Нет");

        RedisProperties redis = cache.getRedisProperties();
        addItem(table, "[Redis] включен", redis.isEnabled() && cache.isCacheEnabled() ? "Да" : "Нет");
        addItem(table, "[Redis] хост", redis.getHost());
        addItem(table, "[Redis] порт", redis.getPort().toString());
        addItem(table, "[Redis] использовать пул", redis.getUsePool() ? "Да" : "Нет");
        addItem(table, "[Redis] пароль", redis.getPassword().isEmpty() ? "Не установлен" : redis.getPassword());
        return table;
    }

    private Table createPropertiesTable(HibernateProperties hibernate) {
        Table table = createTemplateTable("Hibernate");
        addItem(table, "hibernate.hbm2ddl.auto", hibernate.getHibernateHbm2ddlAuto());
        addItem(table, "hibernate.show_sql", hibernate.getHibernateShowSql().equalsIgnoreCase("true") ? "Да" : "Нет");
        addItem(table, "hibernate.dialect", hibernate.getHibernateDialect());
        return table;
    }

    private Table createPropertiesTable(DefaultUserProperties user) {
        Table table = createTemplateTable("Пользователь по умолчанию");
        addItem(table, "Логин", user.getLogin());
        addItem(table, "Пароль", user.getPassword());
        addItem(table, "Роль", RoleTranslator.translate(user.getRole()));
        return table;
    }

    private Table createPropertiesTable(JdbcProperties jdbc) {
        Table table = createTemplateTable("JDBC");
        addItem(table, "Драйвер", jdbc.getJdbcDriverClass());
        addItem(table, "URL", jdbc.getJdbcUrl());
        addItem(table, "Пользователь БД", jdbc.getJdbcUsername());
        addItem(table, "Пароль пользователя", jdbc.getJdbcPassword());
        return table;
    }

    private Table createPropertiesTable(MailProperties mail) {
        Table table = createTemplateTable("Почтовый сервер");
        addItem(table, "Хост", mail.getHost());
        addItem(table, "Порт", String.valueOf(mail.getPort()));
        addItem(table, "Пользователь", mail.getUsername());
        addItem(table, "Пароль", mail.getPassword());
        addItem(table, "SMTP Authentication", mail.getMailSmtpAuth().equalsIgnoreCase("true") ? "Включена" : "Выключена");
        addItem(table, "SMTP SSL", mail.getMailSmtpSslEnable().equalsIgnoreCase("true") ? "Включен" : "Выключен");
        return table;
    }

    private void addItem(Table table, String key, String value) {
        Item item = table.addItem(random(ITEM_ID_LENGTH));
        item.getItemProperty(KEY).setValue(createKeyLabel(key));
        item.getItemProperty(VALUE).setValue(createValueLabel(value));
    }

    private Label createValueLabel(String value) {
        return new Label(format(VALUE_HTML, value), ContentMode.HTML);
    }

    private Label createKeyLabel(String key) {
        return new Label(format(KEY_HTML, key), ContentMode.HTML);
    }

    private Table createTemplateTable(String caption) {
        PlainBorderlessTable table = new PlainBorderlessTable(caption);
        table.addContainerProperty(KEY, Label.class, null);
        table.addContainerProperty(VALUE, Label.class, null);
        table.setWidth(100, Unit.PERCENTAGE);
        table.setColumnWidth(KEY, 500);
        return table;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        update();
    }
}
