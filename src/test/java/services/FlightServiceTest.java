package services;

import model.entities.AirportEntity;
import model.entities.CityEntity;
import model.entities.FlightEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.HibernateUtil;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FlightServiceTest {
    private FlightService flightService = new FlightService();
    private AirportService airportService = new AirportService();
    private CityService cityService = new CityService();

    @BeforeEach
    void setUp() {
        HibernateUtil.getCurrentSession().beginTransaction();
    }

    @AfterEach
    void tearDown() {
        HibernateUtil.getCurrentSession().getTransaction().commit();
    }

    @Test
    void getWithFilter() {
        try {
            List<FlightEntity> flights = new ArrayList<>();
            CityEntity moscow = cityService.getCityByName("Москва");
            List<AirportEntity> mair = moscow.getAirports();
            AirportEntity vityazevo = cityService.getCityByName("Анапа").getAirports().get(0);
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
            HibernateUtil.getCurrentSession().flush();
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
        catch (Exception exc) {
            HibernateUtil.getCurrentSession().getTransaction().rollback();
            throw exc;
        }
    }

    @Test
    void add() {
        try {
            FlightEntity flight = new FlightEntity(airportService.getAirportByName("Домодедово"),
                    airportService.getAirportByName("Витязево"),
                    Timestamp.valueOf("3018-10-10 00:13:13"),
                    Timestamp.valueOf("3018-10-10 13:13:13"),
                    new BigDecimal("1313.13"));
            Serializable id = flightService.add(flight);
            assertTrue(HibernateUtil.getCurrentSession().contains(flight));
            assertEquals((int) id, flight.getId());
            HibernateUtil.getCurrentSession().delete(HibernateUtil.getCurrentSession().load(FlightEntity.class, id));
        }
        catch (Exception exc) {
            HibernateUtil.getCurrentSession().getTransaction().rollback();
            throw exc;
        }
    }

    @Test
    void update() {
        try {
            FlightEntity flight = new FlightEntity(airportService.getAirportByName("Домодедово"),
                    airportService.getAirportByName("Витязево"),
                    Timestamp.valueOf("3018-10-10 00:13:13"),
                    Timestamp.valueOf("3018-10-10 13:13:13"),
                    new BigDecimal("1313.13"));
            Serializable id = flightService.add(flight);
            HibernateUtil.getCurrentSession().getTransaction().commit();
            flight.setAirline("РосАвиаРос");
            HibernateUtil.getCurrentSession().beginTransaction();
            flightService.update(flight);
            assertEquals(HibernateUtil.getCurrentSession().load(FlightEntity.class, id).getAirline(), "РосАвиаРос");
            flightService.remove(flight);
        }
        catch (Exception exc) {
            HibernateUtil.getCurrentSession().getTransaction().rollback();
            throw exc;
        }
    }

    @Test
    void remove() {
        try {
            FlightEntity flight = new FlightEntity(airportService.getAirportByName("Домодедово"),
                    airportService.getAirportByName("Витязево"),
                    Timestamp.valueOf("3018-10-10 00:13:13"),
                    Timestamp.valueOf("3018-10-10 13:13:13"),
                    new BigDecimal("1313.13"));
            flightService.add(flight);
            int prevSize = flightService.getAll().size();
            flightService.remove(flight);
            assertFalse(HibernateUtil.getCurrentSession().contains(flight));
            assertEquals(prevSize - 1, flightService.getAll().size());
        }
        catch (Exception exc) {
            HibernateUtil.getCurrentSession().getTransaction().rollback();
            throw exc;
        }
    }

}