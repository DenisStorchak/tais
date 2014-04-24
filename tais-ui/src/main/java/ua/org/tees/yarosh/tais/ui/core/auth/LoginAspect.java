package ua.org.tees.yarosh.tais.ui.core.auth;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ua.org.tees.yarosh.tais.ui.core.VaadinUtils;

import static ua.org.tees.yarosh.tais.ui.core.api.DataBinds.Cookies.AUTH;

@Aspect
@Component
public class LoginAspect {

    private static final Logger log = LoggerFactory.getLogger(LoginAspect.class);

    /**
     * Need to save auth before ua.org.tees.yarosh.tais.ui.listeners.backend.LoginButtonsInitializer#onLogin invocation
     */
    @Before("execution(* ua.org.tees.yarosh.tais.auth.AuthManager.login(..))")
    public void storeAuth(JoinPoint joinPoint) {
        String auth = (String) joinPoint.getArgs()[0];
        log.info("Store [{}] auth to session", auth);
        VaadinUtils.storeToSession(AUTH, auth);
    }

    /**
     * Delete auth if login failure
     */
    @AfterReturning(pointcut = "execution(* ua.org.tees.yarosh.tais.auth.AuthManager.login(..))", returning = "result")
    public void deleteAuth(boolean result) {
        if (!result) {
            VaadinUtils.storeToSession(AUTH, null);
            log.info("Login failure, auth removed");
        }
    }
}
