package com.airport.service;

import com.airport.model.entities.AirportEntity;
import com.airport.model.entities.FlightEntity;
import com.airport.model.entities.TransferEntity;
import com.airport.util.QueryHolder;
import org.hibernate.Query;
import org.springframework.stereotype.Service;
import com.airport.util.HibernateUtil;
import com.airport.util.TimestampWorker;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("unchecked")
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
                    .createQuery(QueryHolder.GET_FLIGHT_ENTITY_FROM_DATE);
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
                    .createQuery(QueryHolder.REMOVE_FLIGHT_ENTITY_FROM_DATE);
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
                    .createQuery(QueryHolder.GET_FLIGHT_ENTITY_WITH_FILTER);
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
            Integer minTimeTransfer = 0;
            Integer maxTimeTransfer = 8;

            Query query = HibernateUtil.getCurrentSession()
                    .createQuery(QueryHolder.GET_WITH_COMPLEX_FILTER);
            query.setString("cityFrom", cityFrom);
            query.setParameter("date", date);
            query.setParameter("dateFor", dateFor);
            query.setParameter("hCost", cost);
            List<FlightEntity> list = (List<FlightEntity>) query.list();

            List<FlightEntity> firstTransfer = new ArrayList<>();
            List<FlightEntity> secondTransfer = new ArrayList<>();

            if (!list.isEmpty()) {
                Timestamp minDate = list.get(0).getArrivalTime();
                Timestamp maxDate = list.get(0).getArrivalTime();
                FlightForDates flightForDates = new FlightForDates(list, minDate, maxDate).invoke();
                minDate = flightForDates.getMinDate();
                maxDate = flightForDates.getMaxDate();
                // TODO: 06.02.2018 i am too vpadlu to name it
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
                firstTransferQuery.setParameter("hCost", cost);
                firstTransfer = (List<FlightEntity>) firstTransferQuery.list();

                if (!firstTransfer.isEmpty()) {
                    minDate = firstTransfer.get(0).getArrivalTime();
                    maxDate = firstTransfer.get(0).getArrivalTime();
                    flightForDates = new FlightForDates(list, minDate, maxDate).invoke();
                    minDate = flightForDates.getMinDate();
                    maxDate = flightForDates.getMaxDate();

                    // TODO: 06.02.2018 i am too vpadlu to name it
                    Query secondTransferQuery = HibernateUtil.getCurrentSession()
                            .createQuery("select flight from FlightEntity flight " +
                                    "where flight.airportFromId in (:ids) " +
                                    "and flight.departureTime >= :date " +
                                    "and flight.departureTime <= :dateFor " +
                                    "and flight.cost <= :hCost"
                            );
                    secondTransferQuery.setParameterList("ids", firstTransfer.stream()
                            .map(FlightEntity::getAirportToId)
                            .collect(Collectors.toList()));
                    secondTransferQuery.setParameter("date", TimestampWorker.addHours(minDate, minTimeTransfer));
                    secondTransferQuery.setParameter("dateFor", TimestampWorker.addHours(maxDate, maxTimeTransfer));
                    secondTransferQuery.setParameter("hCost", cost);
                    secondTransfer = (List<FlightEntity>) secondTransferQuery.list();
                }
            }

            Query destAirportsQuery = HibernateUtil.getCurrentSession()
                    .createQuery("select air from AirportEntity air " +
                            "where air.city.name = :cityTo");
            destAirportsQuery.setString("cityTo", cityTo);
            List<AirportEntity> destAirportsId = (List<AirportEntity>) destAirportsQuery.list();

            HibernateUtil.getCurrentSession().getTransaction().commit();

            List<List<FlightEntity>> result = new ArrayList<>();
            for (FlightEntity flight : list) {
                if (destAirportsId.contains(flight.getAirportToObject())) {
                    List<FlightEntity> combineFlight = new ArrayList<>();
                    combineFlight.add(flight);
                    result.add(combineFlight);
                }
                else {
                    for (FlightEntity transfer1 : firstTransfer) {
                        if (destAirportsId.contains(transfer1.getAirportToObject()) &&
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
                                if (destAirportsId.contains(transfer2.getAirportToObject()) &&
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

    private class FlightForDates {
        private List<FlightEntity> list;
        private Timestamp minDate;
        private Timestamp maxDate;

        FlightForDates(List<FlightEntity> list, Timestamp minDate, Timestamp maxDate) {
            this.list = list;
            this.minDate = minDate;
            this.maxDate = maxDate;
        }

        Timestamp getMinDate() {
            return minDate;
        }

        Timestamp getMaxDate() {
            return maxDate;
        }

        FlightForDates invoke() {
            for (FlightEntity flight : list) {
                if (minDate.before(flight.getArrivalTime())) {
                    minDate = flight.getArrivalTime();
                }
                if (maxDate.after(flight.getArrivalTime())) {
                    maxDate = flight.getArrivalTime();
                }
            }
            return this;
        }
    }
}
