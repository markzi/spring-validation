package uk.co.markzi.spring_validation.quote.validators;

import uk.co.markzi.spring_validation.quote.validators.LongQuoteIdValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = LongQuoteIdValidator.class)
public @interface ValidateLongQuoteId {
    public abstract String message() default "invalid format";
    public abstract Class<?>[] groups() default {};
    public abstract Class<? extends Payload>[] payload() default {};
}