package controller;

import model.entities.FlightEntity;
import model.view.FlightRequest;
import model.view.FlightResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import service.FlightService;
import util.HibernateUtil;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@RestController
public class FlightController {
    private static FlightService flightService = new FlightService();

    @CrossOrigin
    @RequestMapping("/flights/search")
    public ResponseEntity<List<FlightResponse>> getFlights(@RequestParam("cityFrom")String cityFrom,
                                                           @RequestParam("cityTo")String cityTo,
                                                           @RequestParam("date")String date,
                                                           @RequestParam("cost")String cost) {
        List<FlightEntity> flights =  flightService.getWithFilter(
                cityFrom,
                cityTo,
                Timestamp.valueOf(date),
                new BigDecimal(cost)
        );
        List<FlightResponse> responses = new ArrayList<>();
        for (FlightEntity flightEntity : flights) {
            responses.add(new FlightResponse(flightEntity));
        }
        System.out.println(flights.size());
        System.out.println(responses.size());
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }
}
