package ua.org.tees.yarosh.tais.core.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Timur Yarosh
 *         Date: 13.04.14
 *         Time: 14:04
 */
public class RegexUtils {

    public static String substringMatching(String str, Pattern pattern) {
        if (str == null || pattern == null || str.isEmpty()) return "";
        Matcher matcher = pattern.matcher(str);
        if (matcher.find()) return matcher.group(1);
        return "";
    }
}
