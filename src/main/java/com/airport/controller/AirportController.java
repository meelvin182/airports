package com.airport.controller;

import com.airport.model.entities.AirportEntity;
import com.airport.service.AirportService;
import com.airport.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class AirportController {
    private AirportService airportService;
    private CityService cityService;

    @Autowired
    public AirportController(AirportService airportService, CityService cityService) {
        this.airportService = airportService;
        this.cityService = cityService;
    }

    @CrossOrigin
    @RequestMapping("/getAirports")
    public List<AirportEntity> getAirports() {
        return airportService.getAll();
    }

    @CrossOrigin
    @PostMapping("/airport/new")
    @ResponseBody
    public void addCity(@RequestParam String name,
                        @RequestParam String cityName,
                        @RequestParam String parallel,
                        @RequestParam String meridian) {
        AirportEntity entity = new AirportEntity(name,
                cityService.getCityByName(cityName),
                new BigDecimal(parallel),
                new BigDecimal(meridian));
        airportService.add(entity);
    }

    @CrossOrigin
    @PostMapping("/airport/delete")
    @ResponseBody
    public void deleteCity(@RequestBody String name) {
        AirportEntity entity = airportService.getAirportByName(name);
        airportService.remove(entity);
    }

}
