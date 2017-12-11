package service;

import model.entities.AirportEntity;
import model.entities.CityEntity;
import util.HibernateUtil;

import java.util.List;

public class AirportService extends AbstractService<AirportEntity> {
    public AirportService() {
        super(AirportEntity.class);
    }

    public AirportEntity getAirportByName(String name) {
        try {
            HibernateUtil.getCurrentSession().beginTransaction();
            AirportEntity airportEntity = (AirportEntity) HibernateUtil.getCurrentSession()
                    .createQuery("SELECT airport FROM AirportEntity airport WHERE airport.name = :name")
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
                    .createQuery("SELECT airport FROM AirportEntity airport WHERE airport.city.name = :name")
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
