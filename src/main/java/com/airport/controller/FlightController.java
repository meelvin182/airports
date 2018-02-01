package com.airport.controller;

import com.airport.model.entities.FlightEntity;
import com.airport.model.view.FlightResponse;
import com.airport.service.AirportService;
import com.airport.util.FlightForDateLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.airport.service.FlightService;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@RestController
public class FlightController {
    private FlightService flightService;
    private AirportService airportService;
    private FlightForDateLoader flightForDateLoader;

    @Autowired
    public FlightController(FlightService flightService,
                            AirportService airportService,
                            FlightForDateLoader flightForDateLoader) {
        this.flightService = flightService;
        this.airportService = airportService;
        this.flightForDateLoader = flightForDateLoader;
    }

    @CrossOrigin
    @RequestMapping("/api/search")
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

    @CrossOrigin
    @RequestMapping("/api/date/search")
    public ResponseEntity<List<FlightResponse>> getDateFlights(@RequestParam("date")String date) {
        List<FlightEntity> flights =  flightService.getFromDate(Timestamp.valueOf(date));
        List<FlightResponse> responses = new ArrayList<>();
        for (FlightEntity flightEntity : flights) {
            responses.add(new FlightResponse(flightEntity));
        }
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping("/api/flight")
    @ResponseBody
    public void addFlight(@RequestBody FlightResponse flight) {
        FlightEntity flightEntity = new FlightEntity(airportService.getAirportByName(flight.getAirportFrom()),
                airportService.getAirportByName(flight.getAirportTo()),
                flight.getArrivalTime(),
                flight.getDepartureTime(),
                flight.getCost()
                );
        flightEntity.setAirline(flight.getAirline());
        flightEntity.setAlwaysLate(flight.getAlwaysLate());
        flightEntity.setFreePlace(flight.getFreePlace());
        flightService.add(flightEntity);
    }

    @CrossOrigin
    @DeleteMapping("/api/flight")
    @ResponseBody
    public void deleteFlight(@RequestBody FlightResponse flight) {
        FlightEntity flightEntity = new FlightEntity(airportService.getAirportByName(flight.getAirportFrom()),
                airportService.getAirportByName(flight.getAirportTo()),
                flight.getArrivalTime(),
                flight.getDepartureTime(),
                flight.getCost()
        );
        flightEntity.setAirline(flight.getAirline());
        flightEntity.setAlwaysLate(flight.getAlwaysLate());
        flightEntity.setFreePlace(flight.getFreePlace());
        flightService.remove(flightEntity);
    }

    @CrossOrigin
    @RequestMapping("/flights/load")
    public void loadForDate(@RequestParam String date,
                            @RequestParam String[] airports,
                            @RequestParam int forEachNumber) {
        flightForDateLoader.loadFlightForDate(Timestamp.valueOf(date), airports, forEachNumber);
    }

    @CrossOrigin
    @RequestMapping("/flights/date/remove")
    public void removeFromDate(@RequestParam String date) {
        flightService.removeFromDate(Timestamp.valueOf(date));
    }

}
