package com.airport.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.List;

@JsonIgnoreProperties(value = {"airports", "id"})
@Entity
@Table(name = "cities", schema = "public", catalog = "airports")
public class CityEntity {
    private int id;
    private String name;
    private List<AirportEntity> airports;

    public CityEntity() {}
    public CityEntity(String name) {
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CityIdGenerator")
    @SequenceGenerator(
            initialValue = 1,
            allocationSize = 1,
            name = "CityIdGenerator",
            sequenceName = "cities_id_seq"
    )
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    protected void setId(int id) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CityEntity that = (CityEntity) o;

        return name != null ? name.equals(that.name) : that.name == null;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "city")
    public List<AirportEntity> getAirports() {
        return airports;
    }

    public void setAirports(List<AirportEntity> airportsById) {
        this.airports = airportsById;
    }
}
