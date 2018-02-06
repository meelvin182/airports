package com.airport.service;

import com.airport.model.entities.CityEntity;
import com.airport.util.HibernateUtil;
import com.airport.util.QueryHolder;
import org.springframework.stereotype.Service;

@Service
public class CityService extends AbstractService<CityEntity>{
    public CityService() {
        super(CityEntity.class);
    }

    public CityEntity getCityByName(String name) {
        try {
            HibernateUtil.getCurrentSession().beginTransaction();
            CityEntity cityEntity = (CityEntity) HibernateUtil.getCurrentSession()
                    .createQuery(QueryHolder.GET_CITY_BY_NAME)
                    .setString("name", name)
                    .uniqueResult();
            HibernateUtil.getCurrentSession().getTransaction().commit();
            return cityEntity;
        }
        catch (Exception exc) {
            HibernateUtil.getCurrentSession().getTransaction().rollback();
            throw exc;
        }
    }

}
