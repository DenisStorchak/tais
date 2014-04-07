package ua.org.tees.yarosh.tais.ui.core.validators;

import com.vaadin.data.validator.AbstractValidator;

public class NotNullValidator extends AbstractValidator<Object> {
    /**
     * Constructs a validator with the given error message.
     *
     * @param errorMessage the message to be included in an {@link com.vaadin.data.Validator.InvalidValueException}
     *                     (with "{0}" replaced by the value that failed validation).
     */
    public NotNullValidator(String errorMessage) {
        super(errorMessage);
    }

    public NotNullValidator() {
        super("Поле должно быть заполнено");
    }

    @Override
    protected boolean isValidValue(Object value) {
        return value != null;
    }

    @Override
    public Class<Object> getType() {
        return Object.class;
    }
}
