package com.airport.service;

import com.airport.model.entities.AirportEntity;
import com.airport.model.entities.FlightEntity;
import com.airport.model.entities.TransferEntity;
import org.junit.jupiter.api.Test;

import java.io.Serializable;

import static org.junit.jupiter.api.Assertions.*;

class TransferServiceTest {
    private TransferService transferService = new TransferService();
    private FlightService flightService = new FlightService();
    private AirportService airportService = new AirportService();

    @Test
    void add() {
            AirportEntity airport = airportService.getAirportByName("Абакан");
            FlightEntity flight = flightService.getAll().get(0);
            TransferEntity entity = new TransferEntity(flight, airport);
            Serializable id = transferService.add(entity);
            assertEquals((int) id, entity.getId());
            transferService.remove(entity);
    }

    @Test
    void remove() {
            AirportEntity airport = airportService.getAirportByName("Домодедово");
            FlightEntity flight = flightService.getAll().get(0);
            TransferEntity entity = new TransferEntity(flight, airport);
            transferService.add(entity);
            int prevSize = transferService.getAll().size();
            transferService.remove(entity);
            assertEquals(prevSize - 1, transferService.getAll().size());
    }

}