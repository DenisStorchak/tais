package ua.org.tees.yarosh.tais.ui.configuration;

import org.springframework.context.annotation.*;
import ua.org.tees.yarosh.tais.ui.core.UIContext;

import static com.vaadin.server.VaadinServlet.getCurrent;
import static org.springframework.web.context.support.WebApplicationContextUtils.getRequiredWebApplicationContext;

/**
 * @author Timur Yarosh
 *         Date: 23.03.14
 *         Time: 10:51
 */
@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
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
