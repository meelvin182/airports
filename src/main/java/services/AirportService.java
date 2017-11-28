package services;

import model.entities.AirportEntity;
import utils.HibernateUtil;

public class AirportService extends AbstractService<AirportEntity> {
    public AirportService() {
        super(AirportEntity.class);
    }

    public AirportEntity getAirportByName(String name) {
        return (AirportEntity) HibernateUtil.getCurrentSession()
                .createQuery("SELECT airport FROM AirportEntity airport WHERE airport.name = :name")
                .setString("name", name)
                .uniqueResult();
    }
}
