package services;

import model.entities.AirportEntity;
import model.entities.CityEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.HibernateUtil;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AirportServiceTest {
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
    void add() {
        try {
            CityEntity city = cityService.getCityByName("Москва");
            AirportEntity airport = new AirportEntity("Караганда", city,
                    new BigDecimal(13.13), new BigDecimal(13.13));
            Serializable id = airportService.add(airport);
            assertTrue(HibernateUtil.getCurrentSession().contains(airport));
            assertEquals((int)id, airport.getId());
            HibernateUtil.getCurrentSession().flush();
            HibernateUtil.getCurrentSession().delete(HibernateUtil.getCurrentSession().load(AirportEntity.class, id));
        }
        catch (Exception exc) {
            HibernateUtil.getCurrentSession().getTransaction().rollback();
            throw exc;
        }
    }

    @Test
    void getAll() {
        try {
            List<AirportEntity> entity = airportService.getAll();
            assertEquals(entity.size(), 143);
            assertTrue(entity.contains(airportService.getAirportByName("Витязево")));
        }
        catch (Exception exc) {
            HibernateUtil.getCurrentSession().getTransaction().rollback();
            throw exc;
        }
    }

    @Test
    void update() {
        try {
            AirportEntity airport = airportService.getAirportByName("Витязево");
            HibernateUtil.getCurrentSession().getTransaction().commit();
            airport.setParallel(new BigDecimal(13.13));
            HibernateUtil.getCurrentSession().beginTransaction();
            airportService.update(airport);
            assertEquals(airportService.getAirportByName("Витязево").getParallel(), new BigDecimal(13.13));
            airport.setParallel(new BigDecimal(45.002097));
        }
        catch (Exception exc) {
            HibernateUtil.getCurrentSession().getTransaction().rollback();
            throw exc;
        }
    }

    @Test
    void remove() {
        try {
            CityEntity city = cityService.getCityByName("Москва");
            AirportEntity airport = new AirportEntity("Караганда", city,
                    new BigDecimal(13.13), new BigDecimal(13.13));
            airportService.add(airport);
            int prevSize = airportService.getAll().size();
            airportService.remove(airport);
            assertEquals(prevSize - 1, airportService.getAll().size());
            assertFalse(HibernateUtil.getCurrentSession().contains(airport));
        }
        catch (Exception exc) {
            HibernateUtil.getCurrentSession().getTransaction().rollback();
            throw exc;
        }
    }

    @Test
    void getAirportByName() {
        try {
            assertEquals(airportService.getAirportByName("Витязево").getName(), "Витязево");
            assertEquals(airportService.getAirportByName("Караганда"), null);
        } catch (Exception exc) {
            HibernateUtil.getCurrentSession().getTransaction().rollback();
            throw exc;
        }
    }

}