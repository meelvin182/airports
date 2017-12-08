package model.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@JsonIgnoreProperties(value = {"flightsFrom", "flightsTo", "transfersIn"})
@Entity
@Table(name = "airports", schema = "public", catalog = "airports")
public class AirportEntity {
    private int id;
    private String name;
    private Integer cityId;
    private BigDecimal parallel;
    private BigDecimal meridian;
    private CityEntity city;
    private List<FlightEntity> flightsFrom;
    private List<FlightEntity> flightsTo;
    private List<TransferEntity> transfersIn;

    public AirportEntity() {}
    public AirportEntity(String name, CityEntity city, BigDecimal parallel, BigDecimal meridian) {
        this.name = name;
        this.city = city;
        this.cityId = city.getId();
        this.parallel = parallel;
        this.meridian = meridian;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AirportIdGenerator")
    @SequenceGenerator(
            initialValue = 1,
            allocationSize = 1,
            name = "AirportIdGenerator",
            sequenceName = "airports_id_seq"
    )
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

    protected void setCityId(Integer cityId) {
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

        AirportEntity that = (AirportEntity) o;

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
    public CityEntity getCity() {
        return city;
    }

    public void setCity(CityEntity citiesByCityId) {
        this.city = citiesByCityId;
        this.cityId = citiesByCityId.getId();
    }

    @OneToMany(mappedBy = "airportFromObject")
    public List<FlightEntity> getFlightsFrom() {
        return flightsFrom;
    }

    public void setFlightsFrom(List<FlightEntity> flightsFrom) {
        this.flightsFrom = flightsFrom;
    }

    @OneToMany(mappedBy = "airportToObject")
    public List<FlightEntity> getFlightsTo() {
        return flightsTo;
    }

    public void setFlightsTo(List<FlightEntity> flightsTo) {
        this.flightsTo = flightsTo;
    }

    @OneToMany(mappedBy = "airportIn")
    public List<TransferEntity> getTransfersIn() {
        return transfersIn;
    }

    public void setTransfersIn(List<TransferEntity> transfersIn) {
        this.transfersIn = transfersIn;
    }
}
