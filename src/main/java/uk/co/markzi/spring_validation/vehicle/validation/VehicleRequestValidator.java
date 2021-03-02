package uk.co.markzi.spring_validation.vehicle.validation;

import uk.co.markzi.spring_validation.vehicle.VehicleRequest;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

public class VehicleRequestValidator implements ConstraintValidator<ValidateVehicleRequest, VehicleRequest> {

    private static final List<String> MAKES_2_DOOR = Arrays.asList("BMW", "AUDI");

    private static final List<String> MAKES_4_DOOR = Arrays.asList("FORD", "VW");

    @Override
    public void initialize(ValidateVehicleRequest constraintAnnotation) {

    }

    @Override
    public boolean isValid(VehicleRequest vehicleRequest, ConstraintValidatorContext context) {

        boolean valid = true;

        if (vehicleRequest.getDoors() == 2) {
            if (!MAKES_2_DOOR.contains(vehicleRequest.getMake().toUpperCase())) {
                valid = false;
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("make is not a valid 2 door vehicle").addPropertyNode("make").addConstraintViolation();
            }
        }

        if (vehicleRequest.getDoors() == 4) {
            if (!MAKES_4_DOOR.contains(vehicleRequest.getMake().toUpperCase())) {
                valid = false;
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("make is not a valid 4 door vehicle").addPropertyNode("make").addConstraintViolation();
            }
        }

        return valid;
    }
}