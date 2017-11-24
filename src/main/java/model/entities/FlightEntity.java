package model.entities;

import javax.persistence.*;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Collection;

@Entity
@Table(name = "flights", schema = "public", catalog = "airports")
public class FlightEntity {
    private int id;
    private Integer airportFromId;
    private Integer airportToId;
    private Timestamp depatureTime;
    private Timestamp arrivalTime;
    private BigInteger cost;
    private String airline;
    private Boolean alwaysLate;
    private Short freePlace;
    private AirportsEntity airportsByAirportFromId;
    private AirportsEntity airportsByAirportToId;
    private Collection<TransfersEntity> transfersById;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
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
    @Column(name = "depature_time", nullable = true)
    public Timestamp getDepatureTime() {
        return depatureTime;
    }

    public void setDepatureTime(Timestamp depatureTime) {
        this.depatureTime = depatureTime;
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
    public BigInteger getCost() {
        return cost;
    }

    public void setCost(BigInteger cost) {
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
                (depatureTime != null ? depatureTime.equals(that.depatureTime) : that.depatureTime == null) &&
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
        result = 31 * result + (depatureTime != null ? depatureTime.hashCode() : 0);
        result = 31 * result + (arrivalTime != null ? arrivalTime.hashCode() : 0);
        result = 31 * result + (cost != null ? cost.hashCode() : 0);
        result = 31 * result + (airline != null ? airline.hashCode() : 0);
        result = 31 * result + (alwaysLate != null ? alwaysLate.hashCode() : 0);
        result = 31 * result + (freePlace != null ? freePlace.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "airport_from_id", referencedColumnName = "id", insertable = false, updatable = false)
    public AirportsEntity getAirportsByAirportFromId() {
        return airportsByAirportFromId;
    }

    public void setAirportsByAirportFromId(AirportsEntity airportsByAirportFromId) {
        this.airportsByAirportFromId = airportsByAirportFromId;
    }

    @ManyToOne
    @JoinColumn(name = "airport_to_id", referencedColumnName = "id", insertable = false, updatable = false)
    public AirportsEntity getAirportsByAirportToId() {
        return airportsByAirportToId;
    }

    public void setAirportsByAirportToId(AirportsEntity airportsByAirportToId) {
        this.airportsByAirportToId = airportsByAirportToId;
    }

    @OneToMany(mappedBy = "flightsByFlightId")
    public Collection<TransfersEntity> getTransfersById() {
        return transfersById;
    }

    public void setTransfersById(Collection<TransfersEntity> transfersById) {
        this.transfersById = transfersById;
    }
}
