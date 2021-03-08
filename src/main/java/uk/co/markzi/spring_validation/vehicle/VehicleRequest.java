package uk.co.markzi.spring_validation.vehicle;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.hibernate.validator.constraints.Range;
import uk.co.markzi.spring_validation.vehicle.validation.ValidateVehicleRequest;
import uk.co.markzi.spring_validation.validation.groups.ClassValidation;

import javax.validation.GroupSequence;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Getter
@ValidateVehicleRequest(groups = {ClassValidation.class})
@GroupSequence({VehicleRequest.class, ClassValidation.class})
public class VehicleRequest {

    @Min(2)
    @Max(4)
    private final int wheels;

    // this is a hibernate validator constraint
    @Range(min = 2, max = 5)
    private final int doors;

    @NotEmpty
    private final String make;

    @NotEmpty
    private final String model;

    public VehicleRequest(
            @JsonProperty("wheels") int wheels,
            @JsonProperty("doors") int doors,
            @JsonProperty("make") String make,
            @JsonProperty("model") String model) {
        this.wheels = wheels;
        this.doors = doors;
        this.make = make;
        this.model = model;
    }
}
