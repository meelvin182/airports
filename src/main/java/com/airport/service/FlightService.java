package com.airport.service;

import com.airport.model.entities.FlightEntity;
import com.airport.model.entities.TransferEntity;
import org.hibernate.Query;
import org.springframework.stereotype.Service;
import com.airport.util.HibernateUtil;
import com.airport.util.TimestampWorker;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Service
public class FlightService extends AbstractService<FlightEntity>{
    public FlightService() {
        super(FlightEntity.class);
    }

    public List<FlightEntity> getFromDate (Timestamp date) {
        try {
            HibernateUtil.getCurrentSession().beginTransaction();
            Timestamp dateFrom = TimestampWorker.resetTime(date);
            Timestamp dateFor = TimestampWorker.addDays(dateFrom, 1);

            Query query = HibernateUtil.getCurrentSession()
                    .createQuery("select flight from FlightEntity flight " +
                            "where flight.depatureTime >= :date and " +
                            "flight.depatureTime <= :dateFor");
            query.setParameter("date", dateFrom);
            query.setParameter("dateFor", dateFor);
            List<FlightEntity> list = (List<FlightEntity>) query.list();
            HibernateUtil.getCurrentSession().getTransaction().commit();
            return list;
        }
        catch (Exception exc) {
            HibernateUtil.getCurrentSession().getTransaction().rollback();
            throw exc;
        }
    }

    public void removeFromDate(Timestamp date) {
        try {
            HibernateUtil.getCurrentSession().beginTransaction();
            Timestamp dateFrom = TimestampWorker.resetTime(date);
            Timestamp dateFor = TimestampWorker.addDays(dateFrom, 1);

            Query query = HibernateUtil.getCurrentSession()
                    .createQuery("delete from FlightEntity flight " +
                            "where flight.depatureTime >= :date and " +
                            "flight.depatureTime <= :dateFor");
            query.setParameter("date", dateFrom);
            query.setParameter("dateFor", dateFor);
            query.executeUpdate();
            HibernateUtil.getCurrentSession().getTransaction().commit();
        }
        catch (Exception exc) {
            HibernateUtil.getCurrentSession().getTransaction().rollback();
            throw exc;
        }
    }

    public List<FlightEntity> getWithFilter(String cityFrom, String cityTo, Timestamp date, BigDecimal cost) {
        try {
            HibernateUtil.getCurrentSession().beginTransaction();
            Timestamp dateFrom = TimestampWorker.resetTime(date);
            Timestamp dateFor = TimestampWorker.addDays(dateFrom, 1);

            Query query = HibernateUtil.getCurrentSession()
                    .createQuery("select flight from FlightEntity flight " +
                            "where flight.airportFromId in " +
                            "(select airport.id from AirportEntity airport " +
                            "where airport.cityId = " +
                            "(select city.id from CityEntity city " +
                            "where city.name = :cityFrom)) " +
                            "and flight.airportToId in " +
                            "(select airport.id from AirportEntity airport " +
                            "where airport.cityId = " +
                            "(select city.id from CityEntity city " +
                            "where city.name = :cityTo)) " +
                            "and flight.depatureTime >= :date " +
                            "and flight.depatureTime <= :dateFor " +
                            "and flight.cost <= :hCost");
            query.setString("cityFrom", cityFrom);
            query.setString("cityTo", cityTo);
            query.setParameter("date", dateFrom);
            query.setParameter("dateFor", dateFor);
            query.setParameter("hCost", cost);
            List<FlightEntity> list = (List<FlightEntity>) query.list();
            HibernateUtil.getCurrentSession().getTransaction().commit();
            return list;
        }
        catch (Exception exc) {
            HibernateUtil.getCurrentSession().getTransaction().rollback();
            throw exc;
        }
    }

    @Override
    public void remove(FlightEntity entity) {
        try {
            HibernateUtil.getCurrentSession().beginTransaction();
            if (entity.getTransfers() != null) {
                for (TransferEntity transfer : entity.getTransfers()) {
                    HibernateUtil.getCurrentSession().delete(transfer);
                }
            }
            HibernateUtil.getCurrentSession().getTransaction().commit();
            super.remove(entity);
        }
        catch (Exception exc) {
            HibernateUtil.getCurrentSession().getTransaction().rollback();
            throw exc;
        }
    }
}
