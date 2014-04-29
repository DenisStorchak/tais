package ua.org.tees.yarosh.tais.ui.core.auth;

import com.vaadin.server.Page;
import com.vaadin.server.VaadinSession;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ua.org.tees.yarosh.tais.ui.core.UIFactory;

@Aspect
@Component
public class LogoutAspect {

    private static final Logger log = LoggerFactory.getLogger(LogoutAspect.class);

    @AfterReturning(
            pointcut = "execution(* ua.org.tees.yarosh.tais.auth.AuthManager.logout(..))",
            returning = "result"
    )
    public void logLoggingOut(boolean result) {
        if (result) {
            UIFactory.free(VaadinSession.getCurrent());
            VaadinSession.getCurrent().close();
            Page.getCurrent().reload();
        }
    }
}
