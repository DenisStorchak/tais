package ua.org.tees.yarosh.tais.ui.core.validators;

import com.vaadin.data.validator.AbstractStringValidator;

import static ua.org.tees.yarosh.tais.ui.core.text.ErrorMessages.VALUE_TOO_SHORT;
import static ua.org.tees.yarosh.tais.ui.core.text.ErrorMessages.getFormattedMessage;

/**
 * @author Timur Yarosh
 *         Date: 23.03.14
 *         Time: 13:38
 */
public class MinLengthValidator extends AbstractStringValidator {

    private int minLength;

    /**
     * Constructs a validator for strings.
     * <p/>
     * <p>
     * Null and empty string values are always accepted. To reject empty values,
     * set the field being validated as required.
     * </p>
     *
     * @param errorMessage the message to be included in an {@link com.vaadin.data.Validator.InvalidValueException}
     *                     (with "{0}" replaced by the value that failed validation).
     */
    public MinLengthValidator(String errorMessage) {
        super(errorMessage);
    }

    public MinLengthValidator(int minLength) {
        super(getFormattedMessage(VALUE_TOO_SHORT, minLength));
        this.minLength = minLength;
    }

    @Override
    protected boolean isValidValue(String value) {
        return value.length() >= minLength;
    }
}
