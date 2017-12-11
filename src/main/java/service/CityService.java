package service;

import model.entities.AirportEntity;
import model.entities.CityEntity;
import util.HibernateUtil;

public class CityService extends AbstractService<CityEntity>{
    public CityService() {
        super(CityEntity.class);
    }

    public CityEntity getCityByName(String name) {
        try {
            HibernateUtil.getCurrentSession().beginTransaction();
            CityEntity cityEntity = (CityEntity) HibernateUtil.getCurrentSession()
                    .createQuery("SELECT city FROM CityEntity city WHERE city.name = :name")
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
