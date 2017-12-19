package com.airport.util;

import com.airport.model.entities.FlightEntity;
import com.airport.service.AirportService;
import org.junit.jupiter.api.Test;
import com.airport.service.FlightService;

import java.sql.Timestamp;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FlightForDateLoaderTest {
    private FlightService flightService = new FlightService();
    private AirportService airportService = new AirportService();
    private FlightForDateLoader flightForDateLoader = new FlightForDateLoader(flightService, airportService);

    @Test
    void loadFlightForDate() {
        //Timestamp date = new Timestamp(System.currentTimeMillis());
        Timestamp date = Timestamp.valueOf("3018-10-10 00:13:13");
        flightForDateLoader.loadFlightForDate(date, new String[]{"Домодедово"}, 13);
        List<FlightEntity> flights = flightService.getFromDate(date);
        assertEquals(flights.size(), 13);
        for (FlightEntity flightEntity : flights) {
            flightService.remove(flightEntity);
        }
    }

    @Test
    void removeFlightForDate() {
        Timestamp date = new Timestamp(System.currentTimeMillis());
        //Timestamp date = Timestamp.valueOf("3018-10-10 00:13:13");
        flightForDateLoader.loadFlightForDate(date, new String[]{"Домодедово"}, 13);
        flightForDateLoader.removeFlightForDate(date);
        List<FlightEntity> flights = flightService.getFromDate(date);
        assertEquals(flights.size(), 0);
    }

}