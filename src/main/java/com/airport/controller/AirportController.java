package com.airport.controller;

import com.airport.model.entities.AirportEntity;
import com.airport.service.AirportService;
import com.airport.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping(ApiPath.API_PATH)
@CrossOrigin
public class AirportController {
    private AirportService airportService;
    private CityService cityService;

    @Autowired
    public AirportController(AirportService airportService, CityService cityService) {
        this.airportService = airportService;
        this.cityService = cityService;
    }

    @RequestMapping(value = "/airport/", method = GET)
    public ResponseEntity<List<AirportEntity>> getAllAirports() {
        return new ResponseEntity<>(airportService.getAll(), HttpStatus.OK);
    }

    @RequestMapping(value = "/airport/{id}", method = GET)
    public ResponseEntity<AirportEntity> getAirport(@PathVariable("id") long id) {
        // example
        return new ResponseEntity<>(airportService.getAll().get(0), HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping("/airport")
    @ResponseBody
    public void addAirport(@RequestParam String name,
                           @RequestParam String cityName,
                           @RequestParam String parallel,
                           @RequestParam String meridian) {
        AirportEntity entity = new AirportEntity(name,
                cityService.getCityByName(cityName),
                new BigDecimal(parallel),
                new BigDecimal(meridian));
        airportService.add(entity);
    }

    @DeleteMapping("/airport")
    @ResponseBody
    public void deleteCity(@RequestBody String name) {
        AirportEntity entity = airportService.getAirportByName(name);
        airportService.remove(entity);
    }

}
