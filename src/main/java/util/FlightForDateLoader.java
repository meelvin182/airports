package util;

import model.entities.AirportEntity;
import model.entities.FlightEntity;
import model.view.Airline;
import service.AirportService;
import service.FlightService;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class FlightForDateLoader {
    private FlightService flightService = new FlightService();
    private AirportService airportService = new AirportService();

    public void loadFlightForDate(Timestamp date) {
        List<AirportEntity> airports = airportService.getAll();
        Timestamp dateInit = TimestampWorker.resetTime(date);

        Airline[] airlines = Airline.values();

        for (int i = 0; i < 100; i++) {
            ThreadLocalRandom tr = ThreadLocalRandom.current();
            AirportEntity airportFrom = airports.get(tr.nextInt(airports.size()));
            AirportEntity airportTo = airports.get(tr.nextInt(airports.size()));
            while (airportTo == airportFrom) {
                airportTo = airports.get(tr.nextInt(airports.size()));
            }

            Timestamp dateFrom = TimestampWorker.addHours(dateInit, tr.nextInt(23));
            Timestamp dateFor = TimestampWorker.addHours(dateFrom, tr.nextInt(10));
            BigDecimal cost = new BigDecimal(tr.nextInt(70000));
            FlightEntity flight = new FlightEntity(
                    airportFrom, airportTo, dateFrom, dateFor, cost
            );
            flight.setAirline(airlines[tr.nextInt(airlines.length)].getName());
            flight.setAlwaysLate(tr.nextBoolean());
            flight.setFreePlace(new Short(String.valueOf(tr.nextInt(50))));
            flightService.add(flight);
        }
    }

    public void removeFlightForDate(Timestamp date) {
        flightService.removeFromDate(date);
    }
}