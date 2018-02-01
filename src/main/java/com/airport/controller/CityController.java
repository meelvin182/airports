package com.airport.controller;

import com.airport.model.entities.CityEntity;
import com.airport.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CityController {
    private CityService cityService;

    @Autowired
    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @CrossOrigin
    @RequestMapping("/getCities")
    public List<CityEntity> getCities() {
        return cityService.getAll();
    }

    @CrossOrigin
    @PostMapping("/api/city")
    @ResponseBody
    public void addCity(@RequestBody String cityName) {
        CityEntity cityEntity = new CityEntity(cityName);
        cityService.add(cityEntity);
    }

    @CrossOrigin
    @DeleteMapping("/api/city")
    @ResponseBody
    public void deleteCity(@RequestBody String cityName) {
        CityEntity city = cityService.getCityByName(cityName);
        cityService.remove(city);
    }
}