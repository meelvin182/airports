package service;

import model.entities.CityEntity;
import util.HibernateUtil;

public class CityService extends AbstractService<CityEntity>{
    public CityService() {
        super(CityEntity.class);
    }

    public CityEntity getCityByName(String name) {
        return (CityEntity) HibernateUtil.getCurrentSession()
                .createQuery("SELECT city FROM CityEntity city WHERE city.name = :name")
                .setString("name", name)
                .uniqueResult();
    }
}