package model.entities;

import javax.persistence.*;

@Entity
@Table(name = "users", schema = "public", catalog = "airports")
public class UserEntity {
    private int id;
    private String login;
    private String userRole;
    private PasswordEntity passwordById;

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
    @Column(name = "login", nullable = true, length = 46)
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Basic
    @Column(name = "user_role", nullable = true, length = 10)
    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserEntity that = (UserEntity) o;

        return (login != null ? login.equals(that.login) : that.login == null) &&
                (userRole != null ? userRole.equals(that.userRole) : that.userRole == null);
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (userRole != null ? userRole.hashCode() : 0);
        return result;
    }

    @OneToOne(mappedBy = "userById")
    public PasswordEntity getPasswordById() {
        return passwordById;
    }

    public void setPasswordById(PasswordEntity passwordsById) {
        this.passwordById = passwordsById;
    }
}
