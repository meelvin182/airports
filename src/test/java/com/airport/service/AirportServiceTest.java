package com.airport.service;

import com.airport.model.entities.AirportEntity;
import com.airport.model.entities.CityEntity;
import org.junit.jupiter.api.Test;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AirportServiceTest {
    private AirportService airportService = new AirportService();
    private CityService cityService = new CityService();

    @Test
    void add() {
            CityEntity city = cityService.getCityByName("Москва");
            AirportEntity airport = new AirportEntity("Караганда", city,
                    new BigDecimal(13.13), new BigDecimal(13.13));
            Serializable id = airportService.add(airport);
            assertEquals((int)id, airport.getId());
            airportService.remove(airport);
    }

    @Test
    void getAll() {
            List<AirportEntity> entity = airportService.getAll();
            assertEquals(entity.size(), 143);
            assertTrue(entity.contains(airportService.getAirportByName("Витязево")));
    }

    @Test
    void remove() {
            CityEntity city = cityService.getCityByName("Москва");
            AirportEntity airport = new AirportEntity("Караганда", city,
                    new BigDecimal(13.13), new BigDecimal(13.13));
            airportService.add(airport);
            int prevSize = airportService.getAll().size();
            airportService.remove(airport);
            assertEquals(prevSize - 1, airportService.getAll().size());
    }

    @Test
    void getAirportByName() {
            assertEquals(airportService.getAirportByName("Витязево").getName(), "Витязево");
            assertEquals(airportService.getAirportByName("Караганда"), null);
    }
}