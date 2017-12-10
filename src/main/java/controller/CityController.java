package controller;

import model.entities.CityEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import service.CityService;
import util.HibernateUtil;

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