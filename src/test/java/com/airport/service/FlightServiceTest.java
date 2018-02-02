package com.airport.service;

import com.airport.model.entities.AirportEntity;
import com.airport.model.entities.FlightEntity;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Service
class FlightServiceTest {

    private FlightService flightService = new FlightService();
    private AirportService airportService = new AirportService();
    private CityService cityService = new CityService();

    @Test
    void getFromDate() {
            FlightEntity flight = new FlightEntity(airportService.getAirportByName("Домодедово"),
                    airportService.getAirportByName("Витязево"),
                    Timestamp.valueOf("3018-10-10 00:13:13"),
                    Timestamp.valueOf("3018-10-10 13:13:13"),
                    new BigDecimal("1313.13"));
            flightService.add(flight);

            Timestamp date = Timestamp.valueOf("3018-10-10 00:00:00");
            List<FlightEntity> list = flightService.getFromDate(date);
            assertEquals(list.size(), 1);
            for (FlightEntity fl : list) {
                flightService.remove(fl);
            }
    }

    @Test
    void removeFromDate() {
            FlightEntity flight = new FlightEntity(airportService.getAirportByName("Домодедово"),
                    airportService.getAirportByName("Витязево"),
                    Timestamp.valueOf("3018-10-10 00:13:13"),
                    Timestamp.valueOf("3018-10-10 13:13:13"),
                    new BigDecimal("1313.13"));
            flightService.add(flight);

            Timestamp date = Timestamp.valueOf("3018-10-10 00:00:00");

            flightService.removeFromDate(date);
            List<FlightEntity> list = flightService.getFromDate(date);
            assertEquals(list.size(), 0);
    }

    @Test
    void getWithFilter() {
            List<FlightEntity> flights = new ArrayList<>();
            List<AirportEntity> mair = airportService.getAirportsByCityName("Москва");
            AirportEntity vityazevo = airportService.getAirportByName("Витязево");
            for (AirportEntity air : mair) {

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(Timestamp.valueOf("3018-10-10 00:13:13"));
                calendar.add(Calendar.HOUR, mair.indexOf(air) * 4);
                Timestamp departureTime = new Timestamp(calendar.getTimeInMillis());
                calendar.setTime(Timestamp.valueOf("3018-10-10 13:13:13"));
                calendar.add(Calendar.HOUR, mair.indexOf(air) * 4);
                Timestamp arrivalTime = new Timestamp(calendar.getTimeInMillis());

                flights.add(new FlightEntity(air, vityazevo, departureTime, arrivalTime,
                        new BigDecimal("1313.13")));
            }

            List<FlightEntity> wrFl = new ArrayList<>();
            wrFl.add(new FlightEntity(mair.get(0), vityazevo,
                    Timestamp.valueOf("3018-10-11 00:13:13"),
                    Timestamp.valueOf("3018-10-11 13:13:13"),
                    new BigDecimal("1313.13")));
            wrFl.add(new FlightEntity(airportService.getAirportByName("Абакан"),
                    vityazevo, Timestamp.valueOf("3018-10-10 00:13:13"),
                    Timestamp.valueOf("3018-10-10 13:13:13"),
                    new BigDecimal("1313.13")));
            for (FlightEntity fl : flights) {
                flightService.add(fl);
            }
            for (FlightEntity fl : wrFl) {
                flightService.add(fl);
            }
            try {
                List<FlightEntity> filter = flightService.getWithFilter(
                        "Москва",
                        "Анапа",
                        Timestamp.valueOf("3018-10-10 00:00:00"),
                        new BigDecimal("1331.13")
                );

                for (FlightEntity fl : flights) {
                    assertTrue(filter.contains(fl));
                }
                for (FlightEntity fl : wrFl) {
                    assertFalse(filter.contains(fl));
                }
            }
            finally {
                for (FlightEntity fl : flights) {
                    flightService.remove(fl);
                }
                for (FlightEntity fl : wrFl) {
                    flightService.remove(fl);
                }
            }
    }

    @Test
    void getWithComplexFilter() {
        List<AirportEntity> mair = airportService.getAirportsByCityName("Москва");
        AirportEntity vityazevo = airportService.getAirportByName("Витязево");
        List<FlightEntity> flights = new ArrayList<>();
        for (AirportEntity air : mair) {
            flights.add(new FlightEntity(air, vityazevo,
                    Timestamp.valueOf("3018-10-11 00:13:13"),
                    Timestamp.valueOf("3018-10-11 13:13:13"),
                    new BigDecimal("1313.13")));
        }
        for (FlightEntity fl : flights) {
            flightService.add(fl);
        }
        try {
            List<List<FlightEntity>> flList = flightService.getWithComplexFilter("Москва",
                    "Анапа",
                    Timestamp.valueOf("3018-10-11 00:00:00"),
                    new BigDecimal("50000.0"));
            assertEquals(flList.size(), mair.size());
            System.out.println(flList);
        }
        finally {
            for (FlightEntity fl : flights) {
                flightService.remove(fl);
            }
        }
    }

    @Test
    void add() {
            FlightEntity flight = new FlightEntity(airportService.getAirportByName("Домодедово"),
                    airportService.getAirportByName("Витязево"),
                    Timestamp.valueOf("3018-10-10 00:13:13"),
                    Timestamp.valueOf("3018-10-10 13:13:13"),
                    new BigDecimal("1313.13"));
            Serializable id = flightService.add(flight);
            assertEquals((int) id, flight.getId());
            flightService.remove(flight);
    }

    @Test
    void remove() {
            FlightEntity flight = new FlightEntity(airportService.getAirportByName("Домодедово"),
                    airportService.getAirportByName("Витязево"),
                    Timestamp.valueOf("3018-10-10 00:13:13"),
                    Timestamp.valueOf("3018-10-10 13:13:13"),
                    new BigDecimal("1313.13"));
            flightService.add(flight);
            int prevSize = flightService.getAll().size();
            flightService.remove(flight);
            assertEquals(prevSize - 1, flightService.getAll().size());
    }

}