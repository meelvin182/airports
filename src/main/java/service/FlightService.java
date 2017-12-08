package service;

import model.entities.FlightEntity;
import model.entities.TransferEntity;
import org.hibernate.Query;
import util.HibernateUtil;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

public class FlightService extends AbstractService<FlightEntity>{
    public FlightService() {
        super(FlightEntity.class);
    }

    public List<FlightEntity> getWithFilter(String cityFrom, String cityTo, Timestamp date, BigDecimal cost) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
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