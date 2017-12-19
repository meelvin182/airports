package com.airport.util;

import com.airport.model.entities.AirportEntity;
import com.airport.model.entities.FlightEntity;
import com.airport.model.view.Airline;
import com.airport.service.AirportService;
import com.airport.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class FlightForDateLoader {
    private FlightService flightService;
    private AirportService airportService;

    @Autowired
    FlightForDateLoader(FlightService flightService, AirportService airportService) {
        this.flightService = flightService;
        this.airportService = airportService;
    }

    public void loadFlightForDate(Timestamp date, String[] airport_name, int num) {
        List<AirportEntity> airports_from = new ArrayList<>();
        List<AirportEntity> airports_to = airportService.getAll();
        for (String air : airport_name) {
            airports_from.add(airportService.getAirportByName(air));
        }
        Timestamp dateInit = TimestampWorker.resetTime(date);

        Airline[] airlines = Airline.values();

        for (AirportEntity airportFrom : airports_from) {
            for (int i = 0; i < num; i++) {
                ThreadLocalRandom tr = ThreadLocalRandom.current();
                AirportEntity airportTo = airports_to.get(tr.nextInt(airports_to.size()));
                while (airportTo == airportFrom) {
                    System.out.println("ai");
                    airportTo = airports_to.get(tr.nextInt(airports_to.size()));
                }

                Timestamp dateFrom = TimestampWorker.addMinutes(dateInit, tr.nextInt(23*60));
                Timestamp dateFor = TimestampWorker.addMinutes(dateFrom, tr.nextInt(10*60));
                BigDecimal cost = new BigDecimal(tr.nextInt(70000) + 1);
                FlightEntity flight = new FlightEntity(
                        airportFrom, airportTo, dateFrom, dateFor, cost
                );
                flight.setAirline(airlines[tr.nextInt(airlines.length)].getName());
                flight.setAlwaysLate(tr.nextBoolean());
                flight.setFreePlace(new Short(String.valueOf(tr.nextInt(50))));
                flightService.add(flight);
            }
        }
    }

    public void removeFlightForDate(Timestamp date) {
        flightService.removeFromDate(date);
    }
}
