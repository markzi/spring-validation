package uk.co.markzi.spring_validation;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import uk.co.markzi.spring_validation.vehicle.VehicleRequest;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(VehicleRestController.class)
class VehicleRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void test_givenValidVehicle_whenPosting_then200StatusAndTrueResponse() throws Exception {

        VehicleRequest vehicleRequest = new VehicleRequest(
                4,
                4,
                "Ford",
                "Focus"
        );

        this.mockMvc.perform(
                post("/vehicle")
                        .content(new ObjectMapper().writeValueAsString(vehicleRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("true")));

    }

    @Test
    public void test_givenInvalidVehicle_whenPosting_then400Status() throws Exception {

        VehicleRequest vehicleRequest = new VehicleRequest(
                4,
                4,
                "Audi",
                "RS6"
        );

        this.mockMvc.perform(
                post("/vehicle")
                        .content(new ObjectMapper().writeValueAsString(vehicleRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.path", is("/vehicle")))
                .andExpect(jsonPath("$.status", is(400)))
                .andExpect(jsonPath("$.type", is("REQUEST_VALIDATION_ERROR")))
                .andExpect(jsonPath("$.typeDescription", is("The request is not valid")))
                .andExpect(jsonPath("$.errors", hasSize(1)))
                .andExpect(jsonPath("$.errors[0].defaultMessage", is("make is not a valid 4 door vehicle")))
                .andExpect(jsonPath("$.errors[0].rejectedValue", is("Audi")))
                .andExpect(jsonPath("$.errors[0].field", is("make")));
    }

    @Test
    public void test_givenAnId_whenGetting_thenVehicleReturned() throws Exception {

        this.mockMvc.perform(
                get("/vehicle/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.wheels", is(4)))
                .andExpect(jsonPath("$.doors", is(4)))
                .andExpect(jsonPath("$.make", is("Ford")))
                .andExpect(jsonPath("$.model", is("Focus")));

    }

    @Test
    public void test_givenAnIdToSmall_whenGetting_then400Status() throws Exception {

        this.mockMvc.perform(
                get("/vehicle/0")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.path", is("/vehicle/0")))
                .andExpect(jsonPath("$.status", is(400)))
                .andExpect(jsonPath("$.type", is("REQUEST_VALIDATION_ERROR")))
                .andExpect(jsonPath("$.typeDescription", is("The request is not valid")))
                .andExpect(jsonPath("$.errors", hasSize(1)))
                .andExpect(jsonPath("$.errors[0].defaultMessage", is("must be between 1 and 2")))
                .andExpect(jsonPath("$.errors[0].rejectedValue", is("0")))
                .andExpect(jsonPath("$.errors[0].field", is("get.id")));

    }
}
