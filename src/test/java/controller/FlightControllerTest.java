package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.entities.FlightEntity;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FlightControllerTest {
    @Test
    void getFlights() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            FlightEntity[] list = mapper.readValue(new File("localhost.json"), FlightEntity[].class);
            for (FlightEntity f : list) {
                System.out.println(f.getAirportFromObject().getName());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}