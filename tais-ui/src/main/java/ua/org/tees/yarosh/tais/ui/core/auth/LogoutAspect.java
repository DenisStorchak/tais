package ua.org.tees.yarosh.tais.ui.core.auth;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ua.org.tees.yarosh.tais.ui.core.VaadinUtils;

import static ua.org.tees.yarosh.tais.ui.core.api.DataBinds.Cookies.AUTH;
import static ua.org.tees.yarosh.tais.ui.core.api.DataBinds.SessionKeys.COMPONENT_FACTORY;
import static ua.org.tees.yarosh.tais.ui.core.api.DataBinds.SessionKeys.PREVIOUS_VIEW;

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
            VaadinUtils.store(AUTH, null);
            VaadinUtils.store(COMPONENT_FACTORY, null);
            VaadinUtils.store(PREVIOUS_VIEW, null);
        }
    }
}
