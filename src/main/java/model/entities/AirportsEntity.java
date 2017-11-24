package model.entities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;

@Entity
@Table(name = "airports", schema = "public", catalog = "airports")
public class AirportsEntity {
    private int id;
    private String name;
    private Integer cityId;
    private BigDecimal parallel;
    private BigDecimal meridian;
    private CitiesEntity citiesByCityId;
    private Collection<FlightEntity> flightsById;
    private Collection<FlightEntity> flightsById_0;
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
    @Column(name = "name", nullable = true, length = 46)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "city_id", nullable = true)
    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    @Basic
    @Column(name = "latitude", nullable = false, precision = 0)
    public BigDecimal getParallel() {
        return parallel;
    }

    public void setParallel(BigDecimal parallel) {
        this.parallel = parallel;
    }

    @Basic
    @Column(name = "longtitude", nullable = false, precision = 0)
    public BigDecimal getMeridian() {
        return meridian;
    }

    public void setMeridian(BigDecimal meridian) {
        this.meridian = meridian;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AirportsEntity that = (AirportsEntity) o;

        return (name != null ? name.equals(that.name) : that.name == null) &&
                (cityId != null ? cityId.equals(that.cityId) : that.cityId == null) &&
                (parallel != null ? parallel.equals(that.parallel) : that.parallel == null) &&
                (meridian != null ? meridian.equals(that.meridian) : that.meridian == null);
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (cityId != null ? cityId.hashCode() : 0);
        result = 31 * result + (parallel != null ? parallel.hashCode() : 0);
        result = 31 * result + (meridian != null ? meridian.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "city_id", referencedColumnName = "id", insertable = false, updatable = false)
    public CitiesEntity getCitiesByCityId() {
        return citiesByCityId;
    }

    public void setCitiesByCityId(CitiesEntity citiesByCityId) {
        this.citiesByCityId = citiesByCityId;
    }

    @OneToMany(mappedBy = "airportsByAirportFromId")
    public Collection<FlightEntity> getFlightsById() {
        return flightsById;
    }

    public void setFlightsById(Collection<FlightEntity> flightsById) {
        this.flightsById = flightsById;
    }

    @OneToMany(mappedBy = "airportsByAirportToId")
    public Collection<FlightEntity> getFlightsById_0() {
        return flightsById_0;
    }

    public void setFlightsById_0(Collection<FlightEntity> flightsById_0) {
        this.flightsById_0 = flightsById_0;
    }

    @OneToMany(mappedBy = "airportsByAirportWhereId")
    public Collection<TransfersEntity> getTransfersById() {
        return transfersById;
    }

    public void setTransfersById(Collection<TransfersEntity> transfersById) {
        this.transfersById = transfersById;
    }
}
