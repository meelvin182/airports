package service;

import model.entities.CityEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.HibernateUtil;

import java.io.Serializable;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CityServiceTest {
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
            CityEntity city = new CityEntity("Париж");
            Serializable id = cityService.add(city);
            assertTrue(HibernateUtil.getCurrentSession().contains(city));
            assertEquals((int)id, city.getId());
            HibernateUtil.getCurrentSession().flush();
            HibernateUtil.getCurrentSession().delete(HibernateUtil.getCurrentSession().load(CityEntity.class, id));
        }
        catch (Exception exc) {
            HibernateUtil.getCurrentSession().getTransaction().rollback();
            throw exc;
        }
    }

    @Test
    void getAll() {
        try {
            List<CityEntity> entity = cityService.getAll();
            assertEquals(entity.size(), 138);
            assertTrue(entity.contains(new CityEntity("Москва")));
        }
        catch (Exception exc) {
            HibernateUtil.getCurrentSession().getTransaction().rollback();
            throw exc;
        }
    }

    @Test
    void update() {
        try {
            CityEntity city = new CityEntity("Париж");
            Serializable id = cityService.add(city);
            HibernateUtil.getCurrentSession().getTransaction().commit();
            city.setName("Абакан1");
            HibernateUtil.getCurrentSession().beginTransaction();
            cityService.update(city);
            assertEquals(HibernateUtil.getCurrentSession().load(CityEntity.class, id).getName(), "Абакан1");
            cityService.remove(city);
        }
        catch (Exception exc) {
            HibernateUtil.getCurrentSession().getTransaction().rollback();
            throw exc;
        }
    }

    @Test
    void remove() {
        try {
            CityEntity city = new CityEntity("Париж");
            cityService.add(city);
            int prevSize = cityService.getAll().size();
            cityService.remove(city);
            assertEquals(prevSize - 1, cityService.getAll().size());
            assertFalse(HibernateUtil.getCurrentSession().contains(city));
        }
        catch (Exception exc) {
            HibernateUtil.getCurrentSession().getTransaction().rollback();
            throw exc;
        }
    }

    @Test
    void getCityByName() {
        try {
            assertEquals(cityService.getCityByName("Москва").getName(), "Москва");
            assertEquals(cityService.getCityByName("Париж"), null);
        } catch (Exception exc) {
            HibernateUtil.getCurrentSession().getTransaction().rollback();
            throw exc;
        }
    }
}