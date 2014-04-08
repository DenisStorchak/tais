package ua.org.tees.yarosh.tais.ui.core.auth;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ua.org.tees.yarosh.tais.ui.core.VaadinUtils;

import static ua.org.tees.yarosh.tais.ui.core.DataBinds.Cookies.AUTH;

@Aspect
@Component
public class LoginAdvice {

    private static final Logger log = LoggerFactory.getLogger(LoginAdvice.class);

    /**
     * Save authorization cookie
     */
    @AfterReturning(
            pointcut = "execution(* ua.org.tees.yarosh.tais.auth.AuthManager.login(..))",
            returning = "result"
    )
    public void saveAuthCookie(JoinPoint joinPoint, boolean result) {
        if (result) {
            String auth = (String) joinPoint.getArgs()[0];
            VaadinUtils.saveCookie(AUTH, auth);
            log.info("Auth cookie for [{}] stored", auth);
        }
    }
}
