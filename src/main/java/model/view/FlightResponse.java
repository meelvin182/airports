package model.view;

import model.entities.FlightEntity;

import lombok.Data;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
public class FlightResponse implements java.io.Serializable{
    private String airportFrom;
    private String airportTo;
    private String cityFrom;
    private String cityTo;
    private Timestamp depatureTime;
    private Timestamp arrivalTime;
    private BigDecimal cost;
    private String airline;
    private Boolean alwaysLate;
    private Short freePlace;

    public FlightResponse(FlightEntity flightEntity) {
        this.airportFrom = flightEntity.getAirportFromObject().getName();
        this.airportTo = flightEntity.getAirportToObject().getName();
        this.cityFrom = flightEntity.getAirportFromObject().getCity().getName();
        this.cityTo = flightEntity.getAirportToObject().getCity().getName();
        this.depatureTime = flightEntity.getDepatureTime();
        this.arrivalTime = flightEntity.getArrivalTime();
        this.cost = flightEntity.getCost();
        this.airline = flightEntity.getAirline();
        this.alwaysLate = flightEntity.getAlwaysLate();
        this.freePlace = flightEntity.getFreePlace();
    }
}
