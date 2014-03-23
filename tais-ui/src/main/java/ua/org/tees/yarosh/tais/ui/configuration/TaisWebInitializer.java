package ua.org.tees.yarosh.tais.ui.configuration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

public class TaisWebInitializer implements WebApplicationInitializer {
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
        ctx.register(TaisConfiguration.class);
        servletContext.addListener(new ContextLoaderListener(ctx));

        ContextAccessor.addContext(TaisConfiguration.class, ctx);
    }
}
