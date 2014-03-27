package ua.org.tees.yarosh.tais.ui.core.validators;

import com.vaadin.data.validator.AbstractStringValidator;
import com.vaadin.ui.AbstractTextField;

import static ua.org.tees.yarosh.tais.ui.core.text.ErrorMessages.FIELDS_NOT_EQUALS;
import static ua.org.tees.yarosh.tais.ui.core.text.ErrorMessages.getFormattedMessage;

/**
 * @author Timur Yarosh
 *         Date: 23.03.14
 *         Time: 12:55
 */
public class FieldEqualsValidator extends AbstractStringValidator {

    private AbstractTextField referenceField;

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
    public FieldEqualsValidator(String errorMessage) {
        super(errorMessage);
    }

    public FieldEqualsValidator(AbstractTextField referenceField, String fieldType) {
        super(getFormattedMessage(FIELDS_NOT_EQUALS, fieldType));
        this.referenceField = referenceField;
    }

    @Override
    protected boolean isValidValue(String value) {
        return referenceField.getValue().equals(value);
    }
}
