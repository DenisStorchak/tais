package ua.org.tees.yarosh.tais.ui.configuration;

import org.springframework.context.annotation.*;
import ua.org.tees.yarosh.tais.attendance.AttendanceConfiguration;
import ua.org.tees.yarosh.tais.auth.AuthConfiguration;
import ua.org.tees.yarosh.tais.core.common.CommonConfiguration;
import ua.org.tees.yarosh.tais.core.user.mgmt.UserMgmtConfiguration;
import ua.org.tees.yarosh.tais.homework.HomeworkConfiguration;
import ua.org.tees.yarosh.tais.schedule.ScheduleConfiguration;
import ua.org.tees.yarosh.tais.ui.core.api.UIContext;
import ua.org.tees.yarosh.tais.user.comm.configuration.UserCommConfiguration;

import static com.vaadin.server.VaadinServlet.getCurrent;
import static org.springframework.web.context.support.WebApplicationContextUtils.getRequiredWebApplicationContext;

/**
 * @author Timur Yarosh
 *         Date: 23.03.14
 *         Time: 10:51
 */
@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
@Import({UserMgmtConfiguration.class,
        UserCommConfiguration.class,
        ScheduleConfiguration.class,
        AttendanceConfiguration.class,
        CommonConfiguration.class,
        HomeworkConfiguration.class,
        AuthConfiguration.class})
@ComponentScan(basePackages = {"ua.org.tees.yarosh.tais.ui"})
public class UIConfiguration {
    @Bean
    @Scope("prototype")
    public UIContext uiContext() {
        return new UIContext() {
            @Override
            public <T> T getBean(Class<T> clazz) {
                return getRequiredWebApplicationContext(getCurrent().getServletContext()).getBean(clazz);
            }
        };
    }
}
