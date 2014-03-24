package ua.org.tees.yarosh.tais.ui.core.validators;

import com.vaadin.data.validator.AbstractStringValidator;

public class NotBlankValidator extends AbstractStringValidator {
    public NotBlankValidator(String errorMessage) {
        super(errorMessage);
    }

    @Override
    protected boolean isValidValue(String s) {
        return s != null && !s.isEmpty();
    }
}
