package com.airport.service;

import com.airport.model.entities.AirportEntity;
import com.airport.model.entities.FlightEntity;
import com.airport.model.entities.TransferEntity;
import org.hibernate.Query;
import org.springframework.stereotype.Service;
import com.airport.util.HibernateUtil;
import com.airport.util.TimestampWorker;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.System.in;

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
                            "where flight.departureTime >= :date and " +
                            "flight.departureTime <= :dateFor");
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
                            "where flight.departureTime >= :date and " +
                            "flight.departureTime <= :dateFor");
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
                            "and flight.departureTime >= :date " +
                            "and flight.departureTime <= :dateFor " +
                            "and flight.cost <= :hCost");
            query.setString("cityFrom", cityFrom);
            query.setString("cityTo", cityTo);
            query.setParameter("date", date);
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

    public List<List<FlightEntity>> getWithComplexFilter(String cityFrom,
                                                         String cityTo,
                                                         Timestamp date,
                                                         BigDecimal cost) {
        try {
            HibernateUtil.getCurrentSession().beginTransaction();
            Timestamp dateFrom = TimestampWorker.resetTime(date);
            Timestamp dateFor = TimestampWorker.addDays(dateFrom, 1);
            Integer minTimeTransfer = 1;
            Integer maxTimeTransfer = 8;

            Query query = HibernateUtil.getCurrentSession()
                    .createQuery("select flight from FlightEntity flight " +
                            "where flight.airportFromId in " +
                            "(select airport.id from AirportEntity airport " +
                            "where airport.cityId = " +
                            "(select city.id from CityEntity city " +
                            "where city.name = :cityFrom)) " +
                            "and flight.departureTime >= :date " +
                            "and flight.departureTime <= :dateFor " +
                            "and flight.cost <= :hCost"
                            );
            query.setString("cityFrom", cityFrom);
            query.setParameter("date", date);
            query.setParameter("dateFor", dateFor);
            List<FlightEntity> list = (List<FlightEntity>) query.list();
            List<FlightEntity> firstTransfer = new ArrayList<>();
            List<FlightEntity> secondTransfer = new ArrayList<>();

            if (!list.isEmpty()) {
                Timestamp minDate = list.get(0).getArrivalTime();
                Timestamp maxDate = list.get(0).getArrivalTime();
                for (FlightEntity flight : list) {
                    if (minDate.before(flight.getArrivalTime())) {
                        minDate = flight.getArrivalTime();
                    }
                    if (maxDate.after(flight.getArrivalTime())) {
                        maxDate = flight.getArrivalTime();
                    }
                }

                Query firstTransferQuery = HibernateUtil.getCurrentSession()
                        .createQuery("select flight from FlightEntity flight " +
                                "where flight.airportFromId in (:ids) " +
                                "and flight.departureTime >= :date " +
                                "and flight.departureTime <= :dateFor " +
                                "and flight.cost <= :hCost"
                        );
                firstTransferQuery.setParameterList("ids", list.stream()
                        .map(FlightEntity::getAirportToId)
                        .collect(Collectors.toList()));
                firstTransferQuery.setParameter("date", TimestampWorker.addHours(minDate, minTimeTransfer));
                firstTransferQuery.setParameter("dateFor", TimestampWorker.addHours(maxDate, maxTimeTransfer));
                firstTransfer = (List<FlightEntity>) query.list();

                if (!firstTransfer.isEmpty()) {
                    minDate = firstTransfer.get(0).getArrivalTime();
                    maxDate = firstTransfer.get(0).getArrivalTime();
                    for (FlightEntity flight : firstTransfer) {
                        if (minDate.before(flight.getArrivalTime())) {
                            minDate = flight.getArrivalTime();
                        }
                        if (maxDate.after(flight.getArrivalTime())) {
                            maxDate = flight.getArrivalTime();
                        }
                    }

                    Query secondTransferQuery = HibernateUtil.getCurrentSession()
                            .createQuery("select flight from FlightEntity flight " +
                                    "where flight.airportFromId in (:ids) " +
                                    "and flight.departureTime >= :date " +
                                    "and flight.departureTime <= :dateFor " +
                                    "and flight.cost <= :hCost"
                            );
                    firstTransferQuery.setParameterList("ids", firstTransfer.stream()
                            .map(FlightEntity::getAirportToId)
                            .collect(Collectors.toList()));
                    secondTransferQuery.setParameter("date", TimestampWorker.addHours(minDate, minTimeTransfer));
                    secondTransferQuery.setParameter("dateFor", TimestampWorker.addHours(maxDate, maxTimeTransfer));
                    secondTransfer = (List<FlightEntity>) query.list();
                }
            }
            Query destAirportsQuery = HibernateUtil.getCurrentSession()
                    .createQuery("select air.id from AirportEntity air " +
                            "where air.city = :cityTo");
            destAirportsQuery.setString("cityTo", cityTo);
            List<Integer> destAirportsId = (List<Integer>) query.list();

            HibernateUtil.getCurrentSession().getTransaction().commit();

            List<List<FlightEntity>> result = new ArrayList<>();
            for (FlightEntity flight : list) {
                if (destAirportsId.contains(flight.getAirportToId())) {
                    List<FlightEntity> combineFlight = new ArrayList<>();
                    combineFlight.add(flight);
                    result.add(combineFlight);
                }
                else {
                    for (FlightEntity transfer1 : firstTransfer) {
                        if (destAirportsId.contains(transfer1.getAirportToId()) &&
                                (flight.getCost()
                                        .add(transfer1.getCost())
                                        .compareTo(cost) < 0)) {
                            List<FlightEntity> combineFlight = new ArrayList<>();
                            combineFlight.add(flight);
                            combineFlight.add(transfer1);
                            result.add(combineFlight);
                        }
                        else  {
                            for (FlightEntity transfer2 : secondTransfer) {
                                if (destAirportsId.contains(transfer2.getAirportToId()) &&
                                        (flight.getCost()
                                                .add(transfer1.getCost())
                                                .add(transfer2.getCost())
                                                .compareTo(cost) < 0)) {
                                    List<FlightEntity> combineFlight = new ArrayList<>();
                                    combineFlight.add(flight);
                                    combineFlight.add(transfer1);
                                    combineFlight.add(transfer2);
                                    result.add(combineFlight);
                                }
                            }
                        }
                    }
                }
            }
            return  result;
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
