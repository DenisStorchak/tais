package ua.org.tees.yarosh.tais.ui.configuration;

import com.sun.org.apache.xerces.internal.parsers.SecurityConfiguration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import ua.org.tees.yarosh.tais.auth.AuthManager;
import ua.org.tees.yarosh.tais.auth.UserDetails;
import ua.org.tees.yarosh.tais.core.common.dto.Role;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

public class TaisWebInitializer implements WebApplicationInitializer {
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
        ctx.register(TaisConfiguration.class);
        ctx.register(SecurityConfiguration.class);
        servletContext.addListener(new ContextLoaderListener(ctx));

        ctx.setServletContext(servletContext);

        ServletRegistration.Dynamic servlet = servletContext.addServlet("dispatcher", new DispatcherServlet(ctx));
        servlet.addMapping("/");
        servlet.setLoadOnStartup(1);

        AuthManager.setDao(login -> {
//            UserDetails userDetails = null; //todo uncomment
//            Registrant registration = registrantService.getRegistration(login);
//            if (registration != null) {
//                userDetails = new UserDetails();
//                userDetails.setUsername(registration.getLogin());
//                userDetails.setPassword(registration.getPassword());
//                userDetails.setRole(registration.getRole().toString());
//            }
//            return userDetails;

            UserDetails userDetails = new UserDetails();
            userDetails.setUsername("admin");
            userDetails.setPassword("admin");
            userDetails.setRole(Role.GOD.toString());
            return userDetails;
        });
    }
}
