package com.airport.controller;

import com.airport.model.entities.FlightEntity;
import com.airport.model.view.FlightResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.airport.service.FlightService;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@RestController
public class FlightController {
    private FlightService flightService;

    @Autowired
    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

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
