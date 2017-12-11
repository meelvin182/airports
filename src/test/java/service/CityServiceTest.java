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

    @Test
    void add() {
        CityEntity city = new CityEntity("Париж");
        Serializable id = cityService.add(city);
        assertEquals((int)id, city.getId());
        cityService.remove(city);
    }

    @Test
    void getAll() {
        List<CityEntity> entity = cityService.getAll();
        assertEquals(entity.size(), 138);
        assertTrue(entity.contains(new CityEntity("Москва")));
    }

    @Test
    void remove() {
            CityEntity city = new CityEntity("Париж");
            cityService.add(city);
            int prevSize = cityService.getAll().size();
            cityService.remove(city);
            assertEquals(prevSize - 1, cityService.getAll().size());
    }

    @Test
    void getCityByName() {
            assertEquals(cityService.getCityByName("Москва").getName(), "Москва");
            assertEquals(cityService.getCityByName("Париж"), null);

    }
}