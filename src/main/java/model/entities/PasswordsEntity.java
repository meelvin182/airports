package model.entities;

import javax.persistence.*;

@Entity
@Table(name = "passwords", schema = "public", catalog = "airports")
public class PasswordsEntity {
    private int id;
    private String password;
    private UsersEntity usersById;

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
    @Column(name = "password", nullable = true, length = -1)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PasswordsEntity that = (PasswordsEntity) o;

        return password != null ? password.equals(that.password) : that.password == null;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        return result;
    }

    @OneToOne
    @JoinColumn(name = "id", referencedColumnName = "id", nullable = false)
    public UsersEntity getUsersById() {
        return usersById;
    }

    public void setUsersById(UsersEntity usersById) {
        this.usersById = usersById;
    }
}
