package ua.org.tees.yarosh.tais.core.common.api;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

/**
 * @author Timur Yarosh
 *         Date: 09.03.14
 *         Time: 16:54
 */
public abstract class SimpleValidation {

    private static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    public static <T> void validate(T toValid) {
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(toValid);
        if (!constraintViolations.isEmpty()) {
            throw new IllegalArgumentException(collectMessages(constraintViolations));
        }
    }

    private static <T> String collectMessages(Set<ConstraintViolation<T>> constraintViolations) {
        StringBuilder errorMessageBuilder = new StringBuilder("Constraint violation, reasons:\n");
        for (ConstraintViolation<T> constraintViolation : constraintViolations) {
            errorMessageBuilder.append(constraintViolation.getMessage()).append("\n");
        }
        return errorMessageBuilder.toString();
    }
}
