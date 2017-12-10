package service;

import model.entities.FlightEntity;
import model.entities.TransferEntity;
import org.hibernate.Query;
import util.HibernateUtil;
import util.TimestampWorker;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

public class FlightService extends AbstractService<FlightEntity>{
    public FlightService() {
        super(FlightEntity.class);
    }

    public List<FlightEntity> getFromDate (Timestamp date) {
        Timestamp dateFrom = TimestampWorker.resetTime(date);
        Timestamp dateFor = TimestampWorker.addDays(dateFrom, 1);

        Query query = HibernateUtil.getCurrentSession()
                .createQuery("select flight from FlightEntity flight " +
                        "where flight.depatureTime >= :date and " +
                        "flight.depatureTime <= :dateFor");
        query.setParameter("date", dateFrom);
        query.setParameter("dateFor", dateFor);

        return (List<FlightEntity>) query.list();
    }

    public void removeFromDate(Timestamp date) {
        Timestamp dateFrom = TimestampWorker.resetTime(date);
        Timestamp dateFor = TimestampWorker.addDays(dateFrom, 1);

        Query query = HibernateUtil.getCurrentSession()
                .createQuery("delete from FlightEntity flight " +
                        "where flight.depatureTime >= :date and " +
                        "flight.depatureTime <= :dateFor");
        query.setParameter("date", dateFrom);
        query.setParameter("dateFor", dateFor);
        query.executeUpdate();
    }

    public List<FlightEntity> getWithFilter(String cityFrom, String cityTo, Timestamp date, BigDecimal cost) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.DATE, 1);
        Timestamp dateFor = new Timestamp(calendar.getTimeInMillis());

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
        query.setParameter("date", date);
        query.setParameter("dateFor", dateFor);
        query.setParameter("hCost", cost);
        List<FlightEntity> list = (List<FlightEntity>) query.list();
        return list;
    }

    @Override
    public void remove(FlightEntity entity) {
        if (entity.getTransfers() != null) {
            for (TransferEntity transfer : entity.getTransfers()) {
                HibernateUtil.getCurrentSession().delete(transfer);
            }
        }
        super.remove(entity);
    }
}
