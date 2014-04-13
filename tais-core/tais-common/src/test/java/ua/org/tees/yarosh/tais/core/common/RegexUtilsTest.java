package ua.org.tees.yarosh.tais.core.common;

import org.testng.annotations.Test;

import java.util.regex.Pattern;

import static org.testng.Assert.assertEquals;
import static ua.org.tees.yarosh.tais.core.common.RegexUtils.substringMatching;

/**
 * @author Timur Yarosh
 *         Date: 13.04.14
 *         Time: 14:13
 */
public class RegexUtilsTest {

    public static final String FOOBAR = "foobar";

    @Test
    public void testSubstringMatching() {
        assertEquals(substringMatching(FOOBAR, Pattern.compile("foo(.+)")), "bar");
        assertEquals(substringMatching("", Pattern.compile("foo(.+)")), "");
        assertEquals(substringMatching(FOOBAR, null), "");
        assertEquals(substringMatching(null, null), "");
    }
}
