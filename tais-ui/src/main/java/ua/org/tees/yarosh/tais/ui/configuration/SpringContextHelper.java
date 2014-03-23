package ua.org.tees.yarosh.tais.ui.configuration;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;

/**
 * @author Timur Yarosh
 *         Date: 23.03.14
 *         Time: 2:24
 */
public class SpringContextHelper {
    private ApplicationContext ctx;

    public SpringContextHelper(ServletContext servletContext) {
        ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
    }

    public <T> T getBean(Class<T> clazz) {
        return ctx.getBean(clazz);
    }
}
