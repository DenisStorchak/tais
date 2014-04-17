package ua.org.tees.yarosh.tais.ui.core.auth;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ua.org.tees.yarosh.tais.ui.core.VaadinUtils;

import static ua.org.tees.yarosh.tais.ui.core.api.DataBinds.Cookies.AUTH;

@Aspect
@Component
public class LogoutAspect {

    private static final Logger log = LoggerFactory.getLogger(LogoutAspect.class);

    @AfterReturning(
            pointcut = "execution(* ua.org.tees.yarosh.tais.auth.AuthManager.logout(..))",
            returning = "result"
    )
    public void logLoggingOut(JoinPoint joinPoint, boolean result) {
        if (result) {
            VaadinUtils.storeToSession(AUTH, null);
            log.info("Auth cookie for [{}] removed", joinPoint.getArgs()[0]);
        }
    }
}
