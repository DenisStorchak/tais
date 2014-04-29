package ua.org.tees.yarosh.tais.auth;

import com.google.common.eventbus.AsyncEventBus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.org.tees.yarosh.tais.auth.api.LoginListener;
import ua.org.tees.yarosh.tais.auth.api.LogoutListener;
import ua.org.tees.yarosh.tais.auth.api.annotations.PermitAll;
import ua.org.tees.yarosh.tais.auth.api.annotations.PermitRoles;
import ua.org.tees.yarosh.tais.auth.events.LoginEvent;
import ua.org.tees.yarosh.tais.auth.events.LogoutEvent;

import java.util.Map;
import java.util.Optional;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class AuthManager {

    private static final Map<String, UserDetails> AUTHORIZATIONS = new ConcurrentHashMap<>();
    private static final Vector<UserRepositoryAdapter> DAO_ADAPTERS = new Vector<>();
    private static final Logger log = LoggerFactory.getLogger(AuthManager.class);
    private static volatile boolean acceptEmptyStrings = false;
    private static boolean enabled = false;
    private AsyncEventBus eventBus;

    @Autowired
    public void setEventBus(AsyncEventBus eventBus) {
        this.eventBus = eventBus;
    }

    public void addLoginListener(LoginListener listener) {
        eventBus.register(listener);
    }

    public void addLogoutListener(LogoutListener listener) {
        eventBus.register(listener);
    }

    public static void addDao(UserRepositoryAdapter userRepositoryAdapter) {
        DAO_ADAPTERS.add(userRepositoryAdapter);
    }

    public boolean login(String username, String password, String id) {
        if (enabled) {
            if ((username.isEmpty() || password.isEmpty()) && !acceptsEmptyStrings()) {
                log.info("Can't login with id [{}] and password [{}]. Empty string aren't allowed.",
                        username, password);
                return false;
            }
            Optional<UserRepositoryAdapter> dao = DAO_ADAPTERS.stream().filter(d -> d.contains(username)).findFirst();
            if (dao.isPresent()) {
                UserDetails userDetails = dao.get().getUserDetails(username);
                if (userDetails != null && assertPasswords(password, userDetails.getPassword(), dao.get())) {
                    AUTHORIZATIONS.put(id, userDetails);
                    log.info("Registrant [{}] logged in", username);
                    eventBus.post(new LoginEvent(userDetails));
                    return true;
                }
            }
            log.info("Username [{}] not presented inside of any registered dao adapter", username);
            return false;
        }
        throw new AuthException("AuthManager disabled");
    }

    private static boolean assertPasswords(String candidate,
                                           String original,
                                           UserRepositoryAdapter userRepositoryAdapter) {
        return userRepositoryAdapter.normalizePassword(candidate).equals(original);
    }

    public static UserDetails getUserDetails(String sessionId) {
        return AUTHORIZATIONS.get(sessionId);
    }

    public static boolean isAuthorized(String id, Class<?> clazz) {
        if (enabled) {
            if (clazz.getAnnotation(PermitAll.class) != null) {
                return true;
            } else if (id == null || clazz == null) {
                log.warn("null id or clazz taken, null returns");
                return false;
            } else if (AUTHORIZATIONS.containsKey(id)) {
                PermitRoles permitRoles = clazz.getAnnotation(PermitRoles.class);
                UserDetails auth = AUTHORIZATIONS.get(id);
                if (auth != null && permitRoles != null) {
                    for (String s : permitRoles.value()) {
                        if (s.equals(auth.getRole())) return true;
                    }
                    return false;
                }
            }
            return false;
        }
        throw new AuthException("AuthManager disabled");
    }

    public boolean logout(String id) {
        if (enabled) {
            if (AUTHORIZATIONS.containsKey(id)) {
                AUTHORIZATIONS.remove(id);
                log.info("Registrant [{}] logged out", id);
                eventBus.post(new LogoutEvent(AUTHORIZATIONS.get(id)));
                return true;
            }
            return false;
        }
        throw new AuthException("AuthManager disabled");
    }

    public static boolean loggedIn(String username) {
        if (enabled) {
            return AUTHORIZATIONS.containsKey(username);
        }
        throw new AuthException("AuthManager disabled");
    }

    public static boolean acceptsEmptyStrings() {
        return acceptEmptyStrings;
    }

    public static void setAcceptsEmptyStrings(boolean enable) {
        acceptEmptyStrings = enable;
    }

    public static void enable() {
        enabled = true;
    }

    public static void disable() {
        enabled = false;
    }
}
