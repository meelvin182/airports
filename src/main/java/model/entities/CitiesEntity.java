package model.entities;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "cities", schema = "public", catalog = "airports")
public class CitiesEntity {
    private int id;
    private String name;
    private Collection<AirportsEntity> airportsById;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CitiesEntity that = (CitiesEntity) o;

        return name != null ? name.equals(that.name) : that.name == null;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "citiesByCityId")
    public Collection<AirportsEntity> getAirportsById() {
        return airportsById;
    }

    public void setAirportsById(Collection<AirportsEntity> airportsById) {
        this.airportsById = airportsById;
    }
}
