package ua.org.tees.yarosh.tais.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.org.tees.yarosh.tais.auth.annotations.PermitAll;
import ua.org.tees.yarosh.tais.auth.annotations.PermitRoles;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AuthManager {

    private static final Map<String, UserDetails> AUTHORIZATIONS = new ConcurrentHashMap<>();
    private static Logger LOGGER = LoggerFactory.getLogger(AuthManager.class);

    private static AuthDao dao;

    public static void setDao(AuthDao authDao) {
        dao = authDao;
    }

    public static boolean login(String username, String password) {
        UserDetails userDetails = dao.getUserDetails(username);
        if (userDetails != null && userDetails.getPassword().equals(password)) {
            AUTHORIZATIONS.put(username, userDetails);
            LOGGER.info("Registrant [{}] logged in", username);
            return true;
        }
        return false;
    }

    public static boolean isAuthorized(String username, Class<?> clazz) {
        if (clazz.getAnnotation(PermitAll.class) != null) {
            return true;
        } else if (AUTHORIZATIONS.containsKey(username)) {
            PermitRoles permitRoles = clazz.getAnnotation(PermitRoles.class);
            UserDetails auth = AUTHORIZATIONS.get(username);
            if (auth != null && permitRoles != null) {
                int i = Arrays.binarySearch(permitRoles.value(), auth.getRole());
                return i != -1;
            }
        }
        return false;
    }

    public static boolean logout(String username) {
        if (AUTHORIZATIONS.containsKey(username)) {
            AUTHORIZATIONS.remove(username);
            LOGGER.info("Registrant [{}] logged out", username);
            return true;
        }
        return false;
    }


}
