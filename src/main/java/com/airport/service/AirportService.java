package com.airport.service;

import com.airport.model.entities.AirportEntity;
import com.airport.util.HibernateUtil;
import com.airport.util.QueryHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@SuppressWarnings("unchecked")
@Service
public class AirportService extends AbstractService<AirportEntity> {
    public AirportService() {
        super(AirportEntity.class);
    }

    public AirportEntity getAirportByName(String name) {
        try {
            HibernateUtil.getCurrentSession().beginTransaction();
            AirportEntity airportEntity = (AirportEntity) HibernateUtil.getCurrentSession()
                    .createQuery(QueryHolder.GET_AIRPORTS_BY_NAME)
                    .setString("name", name)
                    .uniqueResult();
            HibernateUtil.getCurrentSession().getTransaction().commit();
        return airportEntity;
        }
        catch (Exception exc) {
            HibernateUtil.getCurrentSession().getTransaction().rollback();
            throw exc;
        }
    }

    public List<AirportEntity> getAirportsByCityName(String cityName) {
        try {
            HibernateUtil.getCurrentSession().beginTransaction();
            List<AirportEntity> airports = (List<AirportEntity>) HibernateUtil.getCurrentSession()
                    .createQuery(QueryHolder.GET_AIRPORTS_BY_CITY_NAME)
                    .setString("name", cityName)
                    .list();
            HibernateUtil.getCurrentSession().getTransaction().commit();
            return airports;
        }
        catch (Exception exc) {
            HibernateUtil.getCurrentSession().getTransaction().rollback();
            throw exc;
        }
    }
}
