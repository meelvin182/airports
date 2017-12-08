package controller;

import model.entities.FlightEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import service.FlightService;
import util.HibernateUtil;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@RestController
public class FlightController {
    private static FlightService flightService = new FlightService();

    /*FlightController(FlightService flightService) {
        this.flightService = flightService;
    }*/

    @RequestMapping("/")
    public List<FlightEntity> getFlights() {
        HibernateUtil.getCurrentSession().beginTransaction();
        List<FlightEntity> flights =  flightService.getWithFilter(
                "Москва",
                "Анапа",
                Timestamp.valueOf("3018-10-10 00:00:00"),
                new BigDecimal("1331.13")
        );
        HibernateUtil.getCurrentSession().getTransaction().commit();
        return flights;
    }
}
