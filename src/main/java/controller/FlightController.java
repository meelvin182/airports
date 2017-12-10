package controller;

import model.entities.FlightEntity;
import model.view.FlightRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import service.FlightService;
import util.HibernateUtil;

import java.util.List;

@RestController
public class FlightController {
    private static FlightService flightService = new FlightService();

    /*FlightController(FlightService flightService) {
        this.flightService = flightService;
    }*/

    @RequestMapping("/getFlights")
    public List<FlightEntity> getFlights(@RequestParam("flightRequest")FlightRequest flightRequest) {
        HibernateUtil.getCurrentSession().beginTransaction();
        List<FlightEntity> flights =  flightService.getWithFilter(
                flightRequest.getCityFrom(),
                flightRequest.getCityTo(),
                flightRequest.getDate(),
                flightRequest.getCost()
        );
        HibernateUtil.getCurrentSession().getTransaction().commit();
        return flights;
    }
}
