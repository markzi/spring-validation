package uk.co.markzi.spring_validation.vehicle.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = VehicleRequestValidator.class)
public @interface ValidateVehicleRequest {
    public abstract String message() default "invalid format";
    public abstract Class<?>[] groups() default {};
    public abstract Class<? extends Payload>[] payload() default {};
}