package com.airport.util;

import com.airport.model.entities.FlightEntity;
import org.junit.jupiter.api.Test;
import com.airport.service.FlightService;

import java.sql.Timestamp;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FlightForDateLoaderTest {
    private FlightForDateLoader flightForDateLoader = new FlightForDateLoader();
    private FlightService flightService = new FlightService();

    @Test
    void loadFlightForDate() {
        Timestamp date = new Timestamp(System.currentTimeMillis());
        //Timestamp date = Timestamp.valueOf("3018-10-10 00:13:13");
        flightForDateLoader.loadFlightForDate(date);
        List<FlightEntity> flights = flightService.getFromDate(date);
        assertEquals(flights.size(), 4290);
        for (FlightEntity flightEntity : flights) {
            flightService.remove(flightEntity);
        }
    }

    @Test
    void removeFlightForDate() {
        Timestamp date = new Timestamp(System.currentTimeMillis());
        //Timestamp date = Timestamp.valueOf("3018-10-10 00:13:13");
        flightForDateLoader.loadFlightForDate(date);
        flightForDateLoader.removeFlightForDate(date);
        HibernateUtil.getCurrentSession().flush();
        List<FlightEntity> flights = flightService.getFromDate(date);
        assertEquals(flights.size(), 0);
    }

}