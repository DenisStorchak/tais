package ua.org.tees.yarosh.tais.ui.core.text;

/**
 * @author Timur Yarosh
 *         Date: 23.03.14
 *         Time: 12:51
 */
public abstract class ErrorMessages {
    public static final String EMPTY_VALUE = "Поле не должно быть пустым";
    public static final String FIELDS_NOT_EQUALS = "%s должны совпадать";
    public static final String VALUE_TOO_SHORT = "Минимальное количество символов должно быть не меньше %d";

    public static String getFormattedMessage(String message, Object... params) {
        return String.format(message, params);
    }
}
