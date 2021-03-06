package com.airport.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@JsonIgnoreProperties(value = {"transfers", "id", "airportFromId", "airportToId"})
@Entity
@Table(name = "flights", schema = "public", catalog = "airports")
public class FlightEntity {
    private int id;
    private Integer airportFromId;
    private Integer airportToId;
    private Timestamp departureTime;
    private Timestamp arrivalTime;
    private BigDecimal cost;
    private String airline;
    private Boolean alwaysLate;
    private Short freePlace;
    private AirportEntity airportFromObject;
    private AirportEntity airportToObject;
    private List<TransferEntity> transfers;

    public FlightEntity() {}

    public FlightEntity(AirportEntity airportFromObject, AirportEntity airportToObject, Timestamp departureTime, Timestamp arrivalTime, BigDecimal cost) {
        this.airportFromObject = airportFromObject;
        this.airportFromId = airportFromObject.getId();
        this.airportToObject = airportToObject;
        this.airportToId = airportToObject.getId();
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.cost = cost;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FlightIdGenerator")
    @SequenceGenerator(
            initialValue = 1,
            allocationSize = 1,
            name = "FlightIdGenerator",
            sequenceName = "flight_id_seq"
    )
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "airport_from_id", nullable = true)
    public Integer getAirportFromId() {
        return airportFromId;
    }

    public void setAirportFromId(Integer airportFromId) {
        this.airportFromId = airportFromId;
    }

    @Basic
    @Column(name = "airport_to_id", nullable = true)
    public Integer getAirportToId() {
        return airportToId;
    }

    public void setAirportToId(Integer airportToId) {
        this.airportToId = airportToId;
    }

    @Basic
    @Column(name = "departure_time", nullable = true)
    public Timestamp getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Timestamp depatureTime) {
        this.departureTime = depatureTime;
    }

    @Basic
    @Column(name = "arrival_time", nullable = true)
    public Timestamp getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Timestamp arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    @Basic
    @Column(name = "cost", nullable = true, precision = 0)
    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    @Basic
    @Column(name = "airline", nullable = true, length = 46)
    public String getAirline() {
        return airline;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }

    @Basic
    @Column(name = "always_late", nullable = true)
    public Boolean getAlwaysLate() {
        return alwaysLate;
    }

    public void setAlwaysLate(Boolean alwaysLate) {
        this.alwaysLate = alwaysLate;
    }

    @Basic
    @Column(name = "free_place", nullable = true)
    public Short getFreePlace() {
        return freePlace;
    }

    public void setFreePlace(Short freePlace) {
        this.freePlace = freePlace;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FlightEntity that = (FlightEntity) o;

        return (airportFromId != null ? airportFromId.equals(that.airportFromId) : that.airportFromId == null) &&
                (airportToId != null ? airportToId.equals(that.airportToId) : that.airportToId == null) &&
                (departureTime != null ? departureTime.equals(that.departureTime) : that.departureTime == null) &&
                (arrivalTime != null ? arrivalTime.equals(that.arrivalTime) : that.arrivalTime == null) &&
                (cost != null ? cost.equals(that.cost) : that.cost == null) &&
                (airline != null ? airline.equals(that.airline) : that.airline == null) &&
                (alwaysLate != null ? alwaysLate.equals(that.alwaysLate) : that.alwaysLate == null) &&
                (freePlace != null ? freePlace.equals(that.freePlace) : that.freePlace == null);
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (airportFromId != null ? airportFromId.hashCode() : 0);
        result = 31 * result + (airportToId != null ? airportToId.hashCode() : 0);
        result = 31 * result + (departureTime != null ? departureTime.hashCode() : 0);
        result = 31 * result + (arrivalTime != null ? arrivalTime.hashCode() : 0);
        result = 31 * result + (cost != null ? cost.hashCode() : 0);
        result = 31 * result + (airline != null ? airline.hashCode() : 0);
        result = 31 * result + (alwaysLate != null ? alwaysLate.hashCode() : 0);
        result = 31 * result + (freePlace != null ? freePlace.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "airport_from_id", referencedColumnName = "id", insertable = false, updatable = false)
    public AirportEntity getAirportFromObject() {
        return airportFromObject;
    }

    public void setAirportFromObject(AirportEntity airportsByAirportFromId) {
        this.airportFromObject = airportsByAirportFromId;
    }

    @ManyToOne
    @JoinColumn(name = "airport_to_id", referencedColumnName = "id", insertable = false, updatable = false)
    public AirportEntity getAirportToObject() {
        return airportToObject;
    }

    public void setAirportToObject(AirportEntity airportsByAirportToId) {
        this.airportToObject = airportsByAirportToId;
    }

    @OneToMany(mappedBy = "flight", fetch = FetchType.EAGER)
    public List<TransferEntity> getTransfers() {
        return transfers;
    }

    public void setTransfers(List<TransferEntity> transfersById) {
        this.transfers = transfersById;
    }
}
