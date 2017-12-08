package service;

import model.entities.AirportEntity;
import model.entities.FlightEntity;
import model.entities.TransferEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.HibernateUtil;

import java.io.Serializable;

import static org.junit.jupiter.api.Assertions.*;

class TransferServiceTest {
    private TransferService transferService = new TransferService();
    private FlightService flightService = new FlightService();
    private AirportService airportService = new AirportService();
    @BeforeEach
    void setUp() {
        HibernateUtil.getCurrentSession().beginTransaction();
    }

    @AfterEach
    void tearDown() {
        HibernateUtil.getCurrentSession().getTransaction().commit();
    }

    @Test
    void add() {
        try {
            AirportEntity airport = airportService.getAirportByName("Абакан");
            FlightEntity flight = flightService.getAll().get(0);
            TransferEntity entity = new TransferEntity(flight, airport);
            Serializable id = transferService.add(entity);
            assertTrue(HibernateUtil.getCurrentSession().contains(entity));
            assertEquals((int) id, entity.getId());
            HibernateUtil.getCurrentSession().delete(HibernateUtil.getCurrentSession().load(TransferEntity.class, id));
        }
        catch (Exception exc) {
            HibernateUtil.getCurrentSession().getTransaction().rollback();
            throw exc;
        }
    }

    @Test
    void update() {
        try {
            AirportEntity airport = airportService.getAirportByName("Домодедово");
            AirportEntity airportNext = airportService.getAirportByName("Калининград");
            FlightEntity flight = flightService.getAll().get(0);
            TransferEntity entity = new TransferEntity(flight, airport);
            Serializable id = transferService.add(entity);
            HibernateUtil.getCurrentSession().getTransaction().commit();
            entity.setAirportIn(airportNext);
            HibernateUtil.getCurrentSession().beginTransaction();
            transferService.update(entity);
            assertEquals(HibernateUtil.getCurrentSession().load(TransferEntity.class, id).getAirportIn().getName(), "Калининград");
            transferService.remove(entity);
        }
        catch (Exception exc) {
            HibernateUtil.getCurrentSession().getTransaction().rollback();
            throw exc;
        }
    }

    @Test
    void remove() {
        try {
            AirportEntity airport = airportService.getAirportByName("Домодедово");
            FlightEntity flight = flightService.getAll().get(0);
            TransferEntity entity = new TransferEntity(flight, airport);
            transferService.add(entity);
            int prevSize = transferService.getAll().size();
            transferService.remove(entity);
            assertEquals(prevSize - 1, transferService.getAll().size());
            assertFalse(HibernateUtil.getCurrentSession().contains(entity));
        }
        catch (Exception exc) {
            HibernateUtil.getCurrentSession().getTransaction().rollback();
            throw exc;
        }
    }

}