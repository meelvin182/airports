package model.entities;

import javax.persistence.*;

@Entity
@Table(name = "transfers", schema = "public", catalog = "airports")
public class TransferEntity {
    private int id;
    private Integer flightId;
    private Integer airportWhereId;
    private FlightEntity flight;
    private AirportEntity airportIn;

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
    @Column(name = "flight_id", nullable = true)
    public Integer getFlightId() {
        return flightId;
    }

    public void setFlightId(Integer flightId) {
        this.flightId = flightId;
    }

    @Basic
    @Column(name = "airport_where_id", nullable = true)
    public Integer getAirportWhereId() {
        return airportWhereId;
    }

    public void setAirportWhereId(Integer airportWhereId) {
        this.airportWhereId = airportWhereId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TransferEntity that = (TransferEntity) o;

        return (flightId != null ? flightId.equals(that.flightId) : that.flightId == null) &&
                (airportWhereId != null ? airportWhereId.equals(that.airportWhereId) : that.airportWhereId == null);
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (flightId != null ? flightId.hashCode() : 0);
        result = 31 * result + (airportWhereId != null ? airportWhereId.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "flight_id", referencedColumnName = "id", insertable = false, updatable = false)
    public FlightEntity getFlight() {
        return flight;
    }

    public void setFlight(FlightEntity flightsByFlightId) {
        this.flight = flightsByFlightId;
    }

    @ManyToOne
    @JoinColumn(name = "airport_where_id", referencedColumnName = "id", insertable = false, updatable = false)
    public AirportEntity getAirportIn() {
        return airportIn;
    }

    public void setAirportIn(AirportEntity airportsByAirportWhereId) {
        this.airportIn = airportsByAirportWhereId;
    }
}
