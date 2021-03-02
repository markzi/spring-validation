package uk.co.markzi.spring_validation;

import org.hibernate.validator.constraints.Range;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import uk.co.markzi.spring_validation.vehicle.VehicleRequest;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

@RestController
@RequestMapping("/vehicle")
@Validated
public class VehicleRestController {

    @GetMapping("/{id}")
    public VehicleRequest get(@Range(min = 1, max = 2) @PathVariable("id") Integer id) {

        VehicleRequest vehicleRequest = null;

        switch (id) {
            case 1:
                vehicleRequest = new VehicleRequest(
                        4,
                        4,
                        "Ford",
                        "Focus"
                );
                break;
            case 2:
                vehicleRequest = new VehicleRequest(
                        2,
                        3,
                        "Audi",
                        "RS6"
                );
                break;
            default:
                break;
        }

        return vehicleRequest;
    }

    @PostMapping
    public boolean post(@Valid @RequestBody VehicleRequest vehicleRequest) {
        return true;
    }
}
