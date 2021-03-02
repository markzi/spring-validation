package uk.co.markzi.spring_validation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import uk.co.markzi.spring_validation.vehicle.VehicleRequest;
import uk.co.markzi.spring_validation.vehicle.validation.groups.ClassValidation;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

class VehicleRequestTest {

    private static String validMake = "Audi";
    private static String validModel = "RS6";

    private static int validWheels = 4;
    private static int validDoors = 4;


    @Test
    public void test_givenWheelsBelowMinValue_whenValidating_thenMessageWheelsMinValue() {

        int invalidWheels = 1;

        // given
        VehicleRequest vehicleRequest = new VehicleRequest(
                invalidWheels,
                validDoors,
                validMake,
                validModel
        );

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        // when
        Set<ConstraintViolation<VehicleRequest>> constraintsSet = validator.validate(vehicleRequest);

        // then
        Assertions.assertAll(
                () -> Assertions.assertEquals(1, constraintsSet.size()),
                () -> {
                    constraintsSet.stream().forEach(vehicleRequestConstraintViolation -> {
                        Assertions.assertAll(
                                () -> Assertions.assertEquals("must be greater than or equal to 2", vehicleRequestConstraintViolation.getMessage()),
                                () -> Assertions.assertEquals("wheels", vehicleRequestConstraintViolation.getPropertyPath().toString())
                        );
                    });

                }
        );
    }

    @Test
    public void test_givenWheelsAboveMinValue_whenValidating_thenMessageWheelsMaxValue() {

        int invalidWheels = 5;

        // given
        VehicleRequest vehicleRequest = new VehicleRequest(
                invalidWheels,
                validDoors,
                validMake,
                validModel
        );

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        // when
        Set<ConstraintViolation<VehicleRequest>> constraintsSet = validator.validate(vehicleRequest);

        // then
        Assertions.assertAll(
                () -> Assertions.assertEquals(1, constraintsSet.size()),
                () -> {
                    constraintsSet.stream().forEach(vehicleRequestConstraintViolation -> {
                        Assertions.assertAll(
                                () -> Assertions.assertEquals("must be less than or equal to 4", vehicleRequestConstraintViolation.getMessage()),
                                () -> Assertions.assertEquals("wheels", vehicleRequestConstraintViolation.getPropertyPath().toString())
                        );
                    });

                }
        );
    }

    @Test
    public void test_givenDoorsBelowMinValue_whenValidating_thenHibernateRangeMessage() {

        int invalidDoors = 1;

        // given
        VehicleRequest vehicleRequest = new VehicleRequest(
                validWheels,
                invalidDoors,
                validMake,
                validModel
        );

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        // when
        Set<ConstraintViolation<VehicleRequest>> constraintsSet = validator.validate(vehicleRequest);

        // then
        Assertions.assertAll(
                () -> Assertions.assertEquals(1, constraintsSet.size()),
                () -> {
                    constraintsSet.stream().forEach(vehicleRequestConstraintViolation -> {
                        Assertions.assertAll(
                                () -> Assertions.assertEquals("must be between 2 and 5", vehicleRequestConstraintViolation.getMessage()),
                                () -> Assertions.assertEquals("doors", vehicleRequestConstraintViolation.getPropertyPath().toString())
                        );
                    });

                }
        );
    }

    @Test
    public void test_givenDoorsAboveMaxValue_whenValidating_thenHibernateRangeMessage() {

        int invalidDoors = 6;

        // given
        VehicleRequest vehicleRequest = new VehicleRequest(
                validWheels,
                invalidDoors,
                validMake,
                validModel
        );

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        // when
        Set<ConstraintViolation<VehicleRequest>> constraintsSet = validator.validate(vehicleRequest);

        // then
        Assertions.assertAll(
                () -> Assertions.assertEquals(1, constraintsSet.size()),
                () -> {
                    constraintsSet.stream().forEach(vehicleRequestConstraintViolation -> {
                        Assertions.assertAll(
                                () -> Assertions.assertEquals("must be between 2 and 5", vehicleRequestConstraintViolation.getMessage()),
                                () -> Assertions.assertEquals("doors", vehicleRequestConstraintViolation.getPropertyPath().toString())
                        );
                    });

                }
        );
    }

    @Test
    public void test_givenMakeIsEmpty_whenValidating_thenNotEmptyMessage() {

        String invalidMake = "";

        // given
        VehicleRequest vehicleRequest = new VehicleRequest(
                validWheels,
                validDoors,
                invalidMake,
                validModel
        );

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        // when
        Set<ConstraintViolation<VehicleRequest>> constraintsSet = validator.validate(vehicleRequest);

        // then
        Assertions.assertAll(
                () -> Assertions.assertEquals(1, constraintsSet.size()),
                () -> {
                    constraintsSet.stream().forEach(vehicleRequestConstraintViolation -> {
                        Assertions.assertAll(
                                () -> Assertions.assertEquals("must not be empty", vehicleRequestConstraintViolation.getMessage()),
                                () -> Assertions.assertEquals("make", vehicleRequestConstraintViolation.getPropertyPath().toString())
                        );
                    });
                }
        );
    }

    @Test
    public void test_given2DoorsAndMakeNotA2DoorMake_whenValidating_thenClassValidationMessage() {

        // given
        VehicleRequest vehicleRequest = new VehicleRequest(
                validWheels,
                validDoors,
                validMake,
                validModel
        );

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        // when
        Set<ConstraintViolation<VehicleRequest>> constraintsSet = validator.validate(vehicleRequest, ClassValidation.class);

        // then
        Assertions.assertAll(
                () -> Assertions.assertEquals(1, constraintsSet.size()),
                () -> {
                    constraintsSet.stream().forEach(vehicleRequestConstraintViolation -> {
                        Assertions.assertAll(
                                () -> Assertions.assertEquals("make is not a valid 4 door vehicle", vehicleRequestConstraintViolation.getMessage()),
                                () -> Assertions.assertEquals("make", vehicleRequestConstraintViolation.getPropertyPath().toString()),
                                () -> Assertions.assertEquals("Audi", ((VehicleRequest)vehicleRequestConstraintViolation.getInvalidValue()).getMake())
                        );
                    });
                }
        );
    }

}