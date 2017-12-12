package com.airport.controller;

import com.airport.model.entities.CityEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.airport.service.CityService;
import com.airport.util.HibernateUtil;

import java.util.List;

@RestController
public class CityController {
    private static CityService cityService = new CityService();

    @RequestMapping("/getCities")
    public List<CityEntity> getCities() {
        HibernateUtil.getCurrentSession().beginTransaction();
        List<CityEntity> list = cityService.getAll();
        HibernateUtil.getCurrentSession().getTransaction().commit();
        return list;
    }
}