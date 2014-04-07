package ua.org.tees.yarosh.tais.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.org.tees.yarosh.tais.auth.annotations.PermitAll;
import ua.org.tees.yarosh.tais.auth.annotations.PermitRoles;

import java.util.Map;
import java.util.Optional;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Arrays.binarySearch;

public class AuthManager {

    private static final Map<String, UserDetails> AUTHORIZATIONS = new ConcurrentHashMap<>();
    private static final Vector<UserRepositoryAdapter> DAO_ADAPTERS = new Vector<>();
    private static Logger log = LoggerFactory.getLogger(AuthManager.class);
    private static volatile boolean acceptEmptyStrings = false;

    public static void addDao(UserRepositoryAdapter userRepositoryAdapter) {
        DAO_ADAPTERS.add(userRepositoryAdapter);
    }

    public static boolean login(String username, String password) {
        if ((username.isEmpty() || password.isEmpty()) && !acceptsEmptyStrings()) {
            log.info("Can't login with username [{}] and password [{}]. Empty string aren't allowed.",
                    username, password);
            return false;
        }
        Optional<UserRepositoryAdapter> dao = DAO_ADAPTERS.stream().filter(d -> d.contains(username)).findFirst();
        if (dao.isPresent()) {
            UserDetails userDetails = dao.get().getUserDetails(username);
            if (userDetails != null && assertPasswords(password, userDetails.getPassword(), dao.get())) {
                AUTHORIZATIONS.put(username, userDetails);
                log.info("Registrant [{}] logged in", username);
                return true;
            }
        }
        log.info("Username [{}] not presented inside of any registered dao adapter", username);
        return false;
    }

    private static boolean assertPasswords(String candidate,
                                           String original,
                                           UserRepositoryAdapter userRepositoryAdapter) {
        return userRepositoryAdapter.normalizePassword(candidate).equals(original);
    }

    public static boolean isAuthorized(String username, Class<?> clazz) {
        if (clazz.getAnnotation(PermitAll.class) != null) {
            return true;
        } else if (username == null || clazz == null) {
            log.warn("null username or clazz taken, null returns");
            return false;
        } else if (AUTHORIZATIONS.containsKey(username)) {
            PermitRoles permitRoles = clazz.getAnnotation(PermitRoles.class);
            UserDetails auth = AUTHORIZATIONS.get(username);
            if (auth != null && permitRoles != null) {
                int i = binarySearch(permitRoles.value(), auth.getRole());
                return i >= 0;
            }
        }
        return false;
    }

    public static boolean logout(String username) {
        if (AUTHORIZATIONS.containsKey(username)) {
            AUTHORIZATIONS.remove(username);
            log.info("Registrant [{}] logged out", username);
            return true;
        }
        return false;
    }

    public static boolean acceptsEmptyStrings() {
        return acceptEmptyStrings;
    }

    public static void setAcceptsEmptyStrings(boolean enable) {
        acceptEmptyStrings = enable;
    }
}
